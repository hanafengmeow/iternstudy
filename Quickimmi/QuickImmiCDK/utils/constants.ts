export enum REGION {
    MAIN = 'us-east-1'
//    DEV = 'us-west-1'
}

export enum BRANCH {
    MAIN = 'main'
//    DEV = 'dev'
}

export enum STACK_SUFFIX {
    MAIN = ''
//    DEV = '-dev'
}
export enum ENV_FILE {
    MAIN = '.env.prod'
//    DEV = '.env.dev'
}
export enum HOSTED_ZONE_ID_MAP {
     MAIN =  'Z045320536ONQPECEMK2K'
//    DEV = 'Z045320536ONQPECEMK2K'
}
 //xxxxx
export enum CERTIFICATE_ARN {
     MAIN = 'arn:aws:acm:us-east-1:471112972897:certificate/a1ff6d33-c717-40d8-befd-6af6998974c7'
//    DEV = 'arn:aws:acm:us-west-1:471112972897:certificate/b4c7531d-c62c-423e-8791-5f21766412e7'
}
export enum SECRET_ARN {
    MAIN = 'arn:aws:secretsmanager:us-west-1:471112972897:secret:quickimmiwebserver-github-token-caQmy2',
//    DEV = 'arn:aws:secretsmanager:us-west-1:471112972897:secret:quickimmiwebserver-token-xPu5ae'
}

export interface BranchConfigs {
    readonly branch: BRANCH;
    readonly suffix: STACK_SUFFIX;
    readonly zoneID: string;
    readonly region: REGION;
    readonly envFile: ENV_FILE;
    readonly certificateARN: CERTIFICATE_ARN;
    readonly secretARN: SECRET_ARN;
}

export const BRANCH_CONFIGS: BranchConfigs[] = [
     {
         branch: BRANCH.MAIN,
         suffix: STACK_SUFFIX.MAIN,
         zoneID: HOSTED_ZONE_ID_MAP.MAIN,
         region: REGION.MAIN,
         envFile: ENV_FILE.MAIN,
         certificateARN: CERTIFICATE_ARN.MAIN,
         secretARN: SECRET_ARN.MAIN
     }
//    {
//        branch: BRANCH.DEV,
//        suffix: STACK_SUFFIX.DEV,
//        zoneID: HOSTED_ZONE_ID_MAP.DEV,
//        region: REGION.DEV,
//        envFile: ENV_FILE.DEV,
//        certificateARN: CERTIFICATE_ARN.DEV,
//        secretARN: SECRET_ARN.DEV
//    }
]

