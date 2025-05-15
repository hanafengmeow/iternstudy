import { IVpc, Peer, Port, SecurityGroup } from "aws-cdk-lib/aws-ec2";
import { Duration, Stack, StackProps } from "aws-cdk-lib";
import { Construct } from "constructs";
import { IRepository } from "aws-cdk-lib/aws-ecr";
import { LogGroup } from "aws-cdk-lib/aws-logs"

import {
    Cluster, ContainerDefinition,
    CpuArchitecture,
    EcrImage,
    FargateService,
    FargateTaskDefinition,
    OperatingSystemFamily,
    AwsLogDriver
} from "aws-cdk-lib/aws-ecs";
import {
    ApplicationLoadBalancer,
    ApplicationProtocol,
    ApplicationProtocolVersion,
    ListenerAction,
    SslPolicy
} from "aws-cdk-lib/aws-elasticloadbalancingv2";
import { CERTIFICATE_ARN } from "../utils/constants";


interface Props extends StackProps {
    vpc: IVpc
    repository: IRepository
    certificateARN: CERTIFICATE_ARN
    clusterName: string
    loadBalancerName: string
    targetGroupName: string
}

const CONTAINER_PORT = 8080

export class ElasticContainerStack extends Stack {
    public readonly loadBalancer: ApplicationLoadBalancer
    public readonly container: ContainerDefinition
    public readonly service: FargateService
    public readonly cluster: Cluster

    constructor(scope: Construct, id: string, props: Props) {
        super(scope, id, props)
        this.cluster = new Cluster(this, props.clusterName, {
            vpc: props.vpc,
            clusterName: props.clusterName,
            containerInsights: true,
        })

        //SSL
        const CERTIFICATE_ARN = props.certificateARN;

        const albSg = new SecurityGroup(this, "security-group-load-balancer", {
            vpc: props.vpc,
            allowAllOutbound: true,
        })

        albSg.addIngressRule(Peer.anyIpv4(), Port.tcp(443));

        this.loadBalancer = new ApplicationLoadBalancer(this, props.loadBalancerName, {
            vpc: props.vpc,
            loadBalancerName: props.loadBalancerName,
            internetFacing: true,
            idleTimeout: Duration.minutes(10),
            securityGroup: albSg,
            http2Enabled: false,
            deletionProtection: false,
        })

        const sslListener = this.loadBalancer.addListener('secure https listener', {
            port: 443,
            open: true,
            sslPolicy: SslPolicy.RECOMMENDED,
            certificates: [{ certificateArn: CERTIFICATE_ARN }],
        });

        const httpListener = this.loadBalancer.addListener('http listener', {
            port: 80,
            open: true,
            defaultAction: ListenerAction.redirect({
              port: '443',
              protocol: ApplicationProtocol.HTTPS,
            }),
          });

        const targetGroup = sslListener.addTargets("tcp-listener-target", {
            targetGroupName: props.targetGroupName,
            protocol: ApplicationProtocol.HTTP,
            protocolVersion: ApplicationProtocolVersion.HTTP1,
        }) 

        const taskDefinition = new FargateTaskDefinition(
            this,
            "fargate-task-definition",
            {
                cpu: 512,
                memoryLimitMiB: 1024,
                runtimePlatform: {
                    cpuArchitecture: CpuArchitecture.ARM64,
                    operatingSystemFamily: OperatingSystemFamily.LINUX,
                },

            },
        )

        // add logging
        const logGroup = new LogGroup(this, 'ECS_Log');
        const logDriver = new AwsLogDriver({
            logGroup,
            streamPrefix: 'ecs-stream-prefix',
        });
        this.container = taskDefinition.addContainer("web-server", {
            image: EcrImage.fromEcrRepository(props.repository),
            logging: logDriver
        })

        this.container.addPortMappings({
            containerPort: CONTAINER_PORT,
        })

        const securityGroup = new SecurityGroup(this, "http-sg", {
            vpc: props.vpc,
        })
        securityGroup.addIngressRule(
            Peer.securityGroupId(albSg.securityGroupId),
            Port.tcp(CONTAINER_PORT),
            "Allow inbound connections from ALB"
        )
        this.service = new FargateService(this, "fargate-service", {
            cluster: this.cluster,
            assignPublicIp: false,
            taskDefinition,
            securityGroups: [securityGroup],
            desiredCount: 3,
            minHealthyPercent: 0,
            maxHealthyPercent: 100,
            circuitBreaker: { rollback: true },
        })

        targetGroup.addTarget(this.service)


    }
}
