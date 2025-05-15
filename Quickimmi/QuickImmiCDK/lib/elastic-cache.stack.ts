import {Construct} from "constructs";
import {IVpc, Peer, Port, SecurityGroup} from "aws-cdk-lib/aws-ec2";
import {Duration, Stack, StackProps} from "aws-cdk-lib";
import {CfnReplicationGroup, CfnSubnetGroup, CfnCacheCluster} from "aws-cdk-lib/aws-elasticache"

interface Props extends StackProps {
  vpc: IVpc
}

export class ElasticacheStack extends Stack {
  private static port = 6379
  constructor(scope: Construct, id: string, props: Props) {
    super(scope, id, props);
 
    // Create a security group for the ElastiCache cluster
    const cacheSecurityGroup = new SecurityGroup(this, 'ElastiCacheSecurityGroup', {
      vpc: props.vpc,
      allowAllOutbound: true,
    });

    // Allow inbound traffic on port 6379 (Redis default port) from the same security group
    cacheSecurityGroup.addIngressRule(Peer.ipv4(props.vpc.vpcCidrBlock), Port.tcp(ElasticacheStack.port));
   // cacheSecurityGroup.addIngressRule(Peer.anyIpv4(), Port.allTraffic(), 'Allow all traffic from IPv4');
    // Create the ElastiCache subnet group
    const cacheSubnetGroup = new CfnSubnetGroup(this, 'ElastiCacheSubnetGroup', {
      description: 'ElastiCache subnet group',
      subnetIds: props.vpc.privateSubnets.map((subnet) => subnet.subnetId),
    });

    // Create the ElastiCache cluster
    const cacheCluster = new CfnCacheCluster(this, 'ElastiCacheCluster', {
      cacheNodeType: 'cache.t3.small',
      engine: 'redis',
      numCacheNodes: 1,
      clusterName: 'MyElastiCacheCluster',
      vpcSecurityGroupIds: [cacheSecurityGroup.securityGroupId],
      cacheSubnetGroupName: cacheSubnetGroup.ref,
    });
  }
}