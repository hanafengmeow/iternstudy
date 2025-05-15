# Fargate Deployment

This repository is using AWS CDK v2 and is not compatible with AWS CDK v1 bootstrap stack.

# Pre Request
* Setup hostzone in Route53
* Get SSL certificate for the domain (go to the AWS Certificate Manager (ACM))
* Create S3 bucket with .env.prod config file and update:
  const githubConfig = {
  owner: 'knitdream-ai',
  repo: 'Flow_BE',
  pipelineName: 'flow-pipeline',
  bucketName: 'flow-env',
  projectName: 'flow-build'
  }
* Create Github and AWS integration token:
  Step 1: Create GitHub Personal Access Token:
  1, Go to GitHub.
  2, Click on your profile icon at the top right corner.
  3, Select "Settings".
  4, From the left-hand menu, select "Developer settings".
  5, Click on "Personal access tokens".
  6, Click on "Generate new token".
  7, Give your token a descriptive name.
  8, Select the appropriate scopes. For CodeBuild, you should typically select repo, admin:repo_hook. This grants full control of private repositories and administration of repository hooks.
  9, Generate the token.
  Important: Make sure to copy the token to a secure location; GitHub won't show it again.
  Step 2: Update AWS Secrets Manager with the GitHub Token
  1, Go to AWS Management Console.
  2, Open Secrets Manager.
  3, Either update the existing secret with the new token value or create a new secret.
  4, If creating a new secret, give it a name (you'll reference this in your CDK script).
  5, For the secret value, paste the GitHub personal access token you created.

## Commands:

Run the following commands inside `infrastructure` directory for building, deploying and destroying the stacks

First time setup:
Setup aws cdk in your bash.
Stack region is hardcoded in constants file, change the region if you wish to deploy the stack in other regions.s
```
npm install && npm run build
```
Go to the root folder and run:
```
cdk bootstrap
cdk deploy VpcStack
cdk deploy EcrStack
<!-- cdk deploy ElasticacheStack -->
```
Logged into your AWS account and go to ECR.
Go into the repor created by the cdk package.
**Click "view push commands" to build and push image to ECR**
Create
Use "docker buildx build --platform="linux/arm64" -t ecrstack-flowrepository762a5926-p7nsczzmsk4a ." to build linux image

then run the following
```
cdk deploy --all
```


After Everything has deployed, pipeline should be started automatically, wait for it to finish and view the application, you can find the DNS in EC2 -> Load Balancer



Error: Failed to call ImportSourceCredentials, reason: Access token with server type GITHUB already exists. Delete its source credential and try again.
```
aws codebuild list-source-credentials
aws codebuild delete-source-credentials --arn <existing-credentials-ARN>
```


Manually force a new deployment of the cluster:
```
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 178144850300.dkr.ecr.us-east-1.amazonaws.com
docker buildx build --platform="linux/arm64" -t ecrstack-flowrepository762a5926-p7nsczzmsk4a .
docker tag ecrstack-flowrepository762a5926-p7nsczzmsk4a:latest 178144850300.dkr.ecr.us-east-1.amazonaws.com/ecrstack-flowrepository762a5926-p7nsczzmsk4a:latest
docker push 178144850300.dkr.ecr.us-east-1.amazonaws.com/ecrstack-flowrepository762a5926-p7nsczzmsk4a:latest
aws ecs update-service --cluster flow-cluster --service ElasticContainerStack-fargateserviceService16837280-CaEeINu6zw7s --force-new-deployment
```

Swagger:
https://api.hephaetech.com/api