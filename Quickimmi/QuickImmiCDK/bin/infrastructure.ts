#!/usr/bin/env node
import { getAccountId } from "@exanubes/cdk-utils";
import * as cdk from 'aws-cdk-lib';
import { Environment } from "aws-cdk-lib";
import 'source-map-support/register';
import { AuroraMySqlCdkV2Stack } from '../lib/db.stack';
import { EcrStack } from "../lib/ecr.stack";
import { ElasticContainerStack } from "../lib/elastic-container.stack";
import { PipelineStack } from "../lib/pipeline.stack";
import { Route53Stack } from '../lib/route53.stack';
import { VpcStack } from "../lib/vpc.stack";
import {CognitoUserPoolStack} from '../lib/cognito.stack';
import {ElasticacheStack} from '../lib/elastic-cache.stack';
import { BRANCH_CONFIGS } from '../utils/constants';


async function start(): Promise<void> {
    const account = await getAccountId()
    const app = new cdk.App()
    BRANCH_CONFIGS.forEach((branchConfig) => {
        const region = branchConfig.region;
        console.log("---------------------------------- Region and Account ----------------------------------");
        console.log("Region:" + region);
        console.log("AWS Account" + account);
        console.log("----------------------------------------------------------------------------------------");

        const env: Environment = { account, region }
        const ecr = new EcrStack(app, EcrStack.name + branchConfig.suffix, { env }, "quickimmiwebserver-repo")
        const vpc = new VpcStack(app, VpcStack.name + branchConfig.suffix, { env }, "quickimmiwebserver-vpc")
         new CognitoUserPoolStack(
             'quickimmi-app-client',
             'You need to verify your email for QuickImmi',
             'Thanks for signing up Your verification code is {####}',
             'quickimmi-user-pool',
             app,
             CognitoUserPoolStack.name + branchConfig.suffix,
             { env }
         );
         const ecache = new ElasticacheStack(app, ElasticacheStack.name + branchConfig.suffix, {
             vpc: vpc.vpc,
             env,
         })
        const dbProps = {
            vpc: vpc.vpc,
            env,
            databaseUsername: "quickImmiAdmin",
            clusterIdentifier: "quickimmi-aurora-db",
            defaultDatabaseName: "QuickImmiAurora"
        }
        const db = new AuroraMySqlCdkV2Stack(app, AuroraMySqlCdkV2Stack.name + branchConfig.suffix, dbProps)

        const ecsProps = {
            vpc: vpc.vpc,
            repository: ecr.repository,
            certificateARN: branchConfig.certificateARN,
            env,
            clusterName: "quickimmiwebserver-cluster",
            loadBalancerName: "quickimmiwebserver-alb",
            targetGroupName: "quickimmi-tcp-target-ecs-service"
        }
        const ecs = new ElasticContainerStack(app, ElasticContainerStack.name + branchConfig.suffix, ecsProps)

        const route53Props = {
            loadBalancer: ecs.loadBalancer,
            suffix: branchConfig.suffix,
            zoneID: branchConfig.zoneID,
            env,
            domain: "quickimmi.ai",
            recordName: "quickimmi-alias-record"
        }

        new Route53Stack(app, Route53Stack.name + branchConfig.suffix, route53Props);

        //update github config
        const githubConfig = {
            owner: 'davidma1991',
            repo: 'QuickImmiWebServer',
            pipelineName: 'quickimmiwebserver-pipeline',
            bucketName: 'quickimmiwebserver-env',
            projectName: 'quickimmiwebserver-build'
        }

        const pipelineProps = {
            repository: ecr.repository,
            service: ecs.service,
            cluster: ecs.cluster,
            container: ecs.container,
            branch: branchConfig.branch,
            envFile: branchConfig.envFile,
            secretARN: branchConfig.secretARN,
            env,
        }
        new PipelineStack(app, PipelineStack.name + branchConfig.suffix, pipelineProps, githubConfig)
    })
}

start().catch(error => {
    console.log(error)
    process.exit(1)
})
