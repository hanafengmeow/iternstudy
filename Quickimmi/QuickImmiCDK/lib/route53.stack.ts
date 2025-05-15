import {ARecord, HostedZone, RecordTarget} from "aws-cdk-lib/aws-route53";
import {IApplicationLoadBalancer} from "aws-cdk-lib/aws-elasticloadbalancingv2";
import {Stack, StackProps} from "aws-cdk-lib";
import {Construct} from "constructs";
import * as targets from 'aws-cdk-lib/aws-route53-targets'
import { STACK_SUFFIX } from "../utils/constants";

interface Props extends StackProps {
    loadBalancer: IApplicationLoadBalancer;
    suffix: STACK_SUFFIX;
    zoneID: string;
    domain: string;
    recordName: string;
}

export class Route53Stack extends Stack {
    constructor(scope: Construct, id: string, props: Props) {
        super(scope, id, props)
        const hostedZone = HostedZone.fromHostedZoneAttributes(
            this,
            "hosted-zone",
            {
                hostedZoneId: props.zoneID,
                zoneName: `api${props.suffix}.${props.domain}`,
            }
        )

        new ARecord(this, props.recordName, {
            zone: hostedZone,
            target: RecordTarget.fromAlias(
                new targets.LoadBalancerTarget(props.loadBalancer)
            ),
        })

    }
}