import { SecretValue, Stack, StackProps } from "aws-cdk-lib";
import {
    BuildEnvironmentVariableType,
    BuildSpec,
    EventAction,
    FilterGroup,
    GitHubSourceCredentials, LinuxArmBuildImage,
    Project,
    Source
} from "aws-cdk-lib/aws-codebuild";
import { Artifact, ArtifactPath, Pipeline } from "aws-cdk-lib/aws-codepipeline";
import {
    CodeBuildAction,
    EcsDeployAction,
    GitHubSourceAction,
    GitHubTrigger
} from "aws-cdk-lib/aws-codepipeline-actions";
import { IRepository } from "aws-cdk-lib/aws-ecr";
import { ContainerDefinition, IBaseService, ICluster } from "aws-cdk-lib/aws-ecs";
import { PolicyStatement } from "aws-cdk-lib/aws-iam";
import { Construct } from "constructs";
import { BRANCH, ENV_FILE, SECRET_ARN } from "../utils/constants";



interface Props extends StackProps {
    repository: IRepository
    service: IBaseService
    cluster: ICluster
    container: ContainerDefinition
    branch: BRANCH
    envFile: ENV_FILE
    secretARN: SECRET_ARN
}

interface GithubConfig {
    owner: string;
    repo: string;
    pipelineName: string;
    bucketName: string;
    projectName: string
}

export class PipelineStack extends Stack {
    constructor(
        scope: Construct,
        id: string,
        props: Props,
        githubConfig: GithubConfig
    ) {
        super(scope, id, props)

        //update secret config
        const secretConfig = {
            arn: props.secretARN,
            id: 'quickimmiwebserver-token'
        }

        new GitHubSourceCredentials(this, "code-build-credentials", {
            accessToken: SecretValue.secretsManager(secretConfig.id),
        })

        const source = Source.gitHub({
            owner: githubConfig.owner,
            repo: githubConfig.repo,
            webhook: true,
            webhookFilters: [
                FilterGroup.inEventOf(EventAction.PUSH).andBranchIs(props.branch),
            ],
        })

        const stack = Stack.of(this)
        const buildSpec = this.getBuildSpec(props.envFile, githubConfig.bucketName)

        const project = new Project(this, "project", {
            projectName: githubConfig.projectName,
            buildSpec,
            source,
            environment: {
                buildImage: LinuxArmBuildImage.AMAZON_LINUX_2_STANDARD_2_0,
                privileged: true,
            },
            environmentVariables: {
                REPOSITORY_URI: {
                    value: props.repository.repositoryUri,
                },
                AWS_ACCOUNT_ID: {
                    value: stack.account,
                },
                AWS_STACK_REGION: {
                    value: stack.region,
                },
                GITHUB_AUTH_TOKEN: {
                    type: BuildEnvironmentVariableType.SECRETS_MANAGER,
                    value: secretConfig.arn,
                },
                CONTAINER_NAME: {
                    value: props.container.containerName,
                },
            },
        })

        project.addToRolePolicy(
            new PolicyStatement({
                actions: ["secretsmanager:GetSecretValue"],
                resources: [secretConfig.arn],
            })
        )

        //to copy env file from s3
        project.addToRolePolicy(
            new PolicyStatement({
                actions: [
                    "s3:GetObject",
                    "s3:ListBucket"
                ],
                resources: ["arn:aws:s3:::*/*"],
            })
        )
        props.repository.grantPullPush(project.grantPrincipal)

        const artifacts = {
            source: new Artifact("Source"),
            build: new Artifact("BuildOutput"),
        }

        const pipelineActions = {
            source: new GitHubSourceAction({
                actionName: "Github",
                owner: githubConfig.owner,
                repo: githubConfig.repo,
                branch: props.branch,
                oauthToken: SecretValue.secretsManager(secretConfig.id),
                output: artifacts.source,
                trigger: GitHubTrigger.WEBHOOK,
            }),
            build: new CodeBuildAction({
                actionName: "CodeBuild",
                project,
                input: artifacts.source,
                outputs: [artifacts.build],
            }),
            deploy: new EcsDeployAction({
                actionName: "ECSDeploy",
                service: props.service,
                imageFile: new ArtifactPath(
                    artifacts.build,
                    "docker_image_definition.json"
                ),
            }),
        }

        const pipeline = new Pipeline(this, "DeployPipeline", {
            pipelineName: githubConfig.pipelineName,
            stages: [
                { stageName: "Source", actions: [pipelineActions.source] },
                { stageName: "Build", actions: [pipelineActions.build] },
                { stageName: "Deploy", actions: [pipelineActions.deploy] },
            ],
        })

    }

    private getBuildSpec(envFile: ENV_FILE, bucketName: string) {
        return BuildSpec.fromObject({
            version: '0.2',
            env: {
                shell: 'bash'
            },
            phases: {
                pre_build: {
                    commands: [
                        'echo logging in to AWS ECR',
                        'aws --version',
                        'echo $AWS_STACK_REGION',
                        'echo $CONTAINER_NAME',
                        'aws ecr get-login-password --region ${AWS_STACK_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_STACK_REGION}.amazonaws.com',
                        'COMMIT_HASH=$(echo $CODEBUILD_RESOLVED_SOURCE_VERSION | cut -c 1-7)',
                        'echo $COMMIT_HASH',
                        'IMAGE_TAG=${COMMIT_HASH:=latest}',
                        'echo $IMAGE_TAG',
                        'echo Downloading environment file...',
                        `aws s3 cp s3://${bucketName}/${envFile} ./.env.server`
                    ],
                },
                build: {
                    commands: [
                        'echo Build started on `date`',
                        'echo Build Docker image',
                        'docker build -f ${CODEBUILD_SRC_DIR}/Dockerfile -t ${REPOSITORY_URI}:latest .',
                        'echo Running "docker tag ${REPOSITORY_URI}:latest ${REPOSITORY_URI}:${IMAGE_TAG}"',
                        'docker tag ${REPOSITORY_URI}:latest ${REPOSITORY_URI}:${IMAGE_TAG}'
                    ],
                },
                post_build: {
                    commands: [
                        'echo Build completed on `date`',
                        'echo Push Docker image',
                        'docker push ${REPOSITORY_URI}:latest',
                        'docker push ${REPOSITORY_URI}:${IMAGE_TAG}',
                        'printf "[{\\"name\\": \\"$CONTAINER_NAME\\", \\"imageUri\\": \\"$REPOSITORY_URI:$IMAGE_TAG\\"}]" > docker_image_definition.json'
                    ]
                }
            },
            artifacts: {
                files: ['docker_image_definition.json']
            },
        })
    }

}

