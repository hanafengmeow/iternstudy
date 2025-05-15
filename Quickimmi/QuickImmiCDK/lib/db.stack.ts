import { Construct } from "constructs";
import {CfnResource, Stack, StackProps, RemovalPolicy} from "aws-cdk-lib";
import {InstanceType, IVpc, Peer, Port, SecurityGroup} from "aws-cdk-lib/aws-ec2";
import { Secret } from "aws-cdk-lib/aws-secretsmanager";
import {
    Credentials,
    DatabaseClusterEngine,
    AuroraPostgresEngineVersion,
    DatabaseCluster,
    SubnetGroup,
    CfnDBCluster,
    CfnDBInstance, ServerlessCluster
} from "aws-cdk-lib/aws-rds"
import { AwsCustomResource, PhysicalResourceId, AwsCustomResourcePolicy } from 'aws-cdk-lib/custom-resources'

interface Props extends StackProps {
    vpc: IVpc
    databaseUsername: string
    clusterIdentifier: string
    defaultDatabaseName: string
}

export class AuroraMySqlCdkV2Stack extends Stack {
   constructor(scope: Construct, id: string, props: Props) {
        super(scope, id, props);

        const dbSecurityGroup = new SecurityGroup(this, 'DbSecurityGroup', {
               vpc: props.vpc,
         })

        dbSecurityGroup.addIngressRule(
           Peer.ipv4(props.vpc.vpcCidrBlock),
           Port.tcp(3306), 'allow inbound traffic from anywhere to the db on port 3306')

       // Dynamically generate the username and password, then store in secrets manager
       const databaseCredentialsSecret = new Secret(
           this,
           "DBCredentialsSecret",
           {
               secretName: id + "-rds-credentials",
               generateSecretString: {
                   secretStringTemplate: JSON.stringify({
                       username: props.databaseUsername,
                   }),
                   excludePunctuation: true,
                   includeSpace: false,
                   generateStringKey: "password",
               },
           }
       );

       const subnetGroup = new SubnetGroup(this, "subnetGroup", {
           description: `Subnetgroup for serverless mysql aurora databasa`,
           vpc: props.vpc,
           vpcSubnets: { onePerAz: true },
       })

       /**
        * Scaling configuration of an Aurora Serverless database cluster.
        *
        * @default - Serverless cluster is automatically paused after 5 minutes of being idle.
        *   minimum capacity: 2 ACU
        *   maximum capacity: 16 ACU
        */
       const cluster = new ServerlessCluster(this, props.clusterIdentifier, {
           engine: DatabaseClusterEngine.AURORA_MYSQL,
           vpc: props.vpc,
           credentials: Credentials.fromSecret(databaseCredentialsSecret),
           clusterIdentifier: props.clusterIdentifier,
           defaultDatabaseName: props.defaultDatabaseName,
           subnetGroup: subnetGroup,
           securityGroups: [dbSecurityGroup],
           enableDataApi: true,
           removalPolicy: RemovalPolicy.DESTROY
       });

    }

}
//
// export class AuroraPostgresCdkV2Stack extends Stack {
//     constructor(scope: Construct, id: string, props: Props) {
//         super(scope, id, props);
//
//         // create a security group for aurora db
//         const dbSecurityGroup = new SecurityGroup(this, 'DbSecurityGroup', {
//             vpc: props.vpc,
//         })
//
//         dbSecurityGroup.addIngressRule(
//             Peer.ipv4(props.vpc.vpcCidrBlock),
//             Port.tcp(5432), // allow inbound traffic on port 5432 (postgres)
//             'allow inbound traffic from anywhere to the db on port 5432'
//         )
//
//         // Dynamically generate the username and password, then store in secrets manager
//         const databaseCredentialsSecret = new Secret(
//             this,
//             "DBCredentialsSecret",
//             {
//                 secretName: id + "-rds-credentials",
//                 generateSecretString: {
//                     secretStringTemplate: JSON.stringify({
//                         username: props.databaseUsername,
//                     }),
//                     excludePunctuation: true,
//                     includeSpace: false,
//                     generateStringKey: "password",
//                 },
//             }
//         );
//
//         const subnetGroup = new SubnetGroup(this, "subnetGroup", {
//             description: `Subnetgroup for serverless postgres aurora databasa`,
//             vpc: props.vpc,
//             vpcSubnets: { onePerAz: true },
//         })
//
//         enum ServerlessInstanceType {
//             SERVERLESS = 'serverless',
//         }
//
//         type CustomInstanceType = ServerlessInstanceType | InstanceType;
//
//         const CustomInstanceType = { ...ServerlessInstanceType, ...InstanceType };
//         const dbClusterInstanceCount = 1;
//
//         const dbCluster = new DatabaseCluster(this, props.clusterIdentifier, {
//             clusterIdentifier: props.clusterIdentifier,
//             credentials: Credentials.fromSecret(databaseCredentialsSecret),
//             engine: DatabaseClusterEngine.auroraPostgres({
//                 version: AuroraPostgresEngineVersion.VER_14_3,
//             }),
//             instances: dbClusterInstanceCount,
//             instanceProps: {
//                 vpc: props.vpc,
//                 autoMinorVersionUpgrade: true,
//                 publiclyAccessible: false,
//                 securityGroups: [dbSecurityGroup],
//                 instanceType: CustomInstanceType.SERVERLESS as unknown as InstanceType,
//             },
//             //storageEncrypted: true,
//             defaultDatabaseName: props.defaultDatabaseName,
//             removalPolicy: RemovalPolicy.DESTROY,
//             cloudwatchLogsExports: ["postgresql"]
//         });
//
//         // We need this becasue CDK v2 does not yet fully support Aurora serverless v2, which sucks...
//
//         const serverlessV2ScalingConfiguration = {
//             MinCapacity: 0.5,
//             MaxCapacity: 8,
//         }
//
//         const dbScalingConfigure = new AwsCustomResource(this, 'DbScalingConfigure', {
//             onCreate: {
//                 service: "RDS",
//                 action: "modifyDBCluster",
//                 parameters: {
//                     DBClusterIdentifier: dbCluster.clusterIdentifier,
//                     ServerlessV2ScalingConfiguration: serverlessV2ScalingConfiguration,
//                 },
//                 physicalResourceId: PhysicalResourceId.of(dbCluster.clusterIdentifier)
//             },
//             onUpdate: {
//                 service: "RDS",
//                 action: "modifyDBCluster",
//                 parameters: {
//                     DBClusterIdentifier: dbCluster.clusterIdentifier,
//                     ServerlessV2ScalingConfiguration: serverlessV2ScalingConfiguration,
//                 },
//                 physicalResourceId: PhysicalResourceId.of(dbCluster.clusterIdentifier)
//             },
//             policy: AwsCustomResourcePolicy.fromSdkCalls({
//                 resources: AwsCustomResourcePolicy.ANY_RESOURCE,
//             }),
//         })
//
//         const cfnDbCluster = dbCluster.node.defaultChild as CfnDBCluster
//         const dbScalingConfigureTarget = dbScalingConfigure.node.findChild("Resource").node.defaultChild as CfnResource
//
//         cfnDbCluster.addPropertyOverride("EngineMode", "provisioned")
//         dbScalingConfigure.node.addDependency(cfnDbCluster)
//
//
//         for (let i = 1; i <= dbClusterInstanceCount; i++) {
//             (dbCluster.node.findChild(`Instance${i}`) as CfnDBInstance).addDependency(dbScalingConfigureTarget)
//         }
//     }
// }