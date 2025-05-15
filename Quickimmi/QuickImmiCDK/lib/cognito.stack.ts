import { Stack, StackProps, RemovalPolicy } from 'aws-cdk-lib';
import * as cognito from 'aws-cdk-lib/aws-cognito';
import { Construct } from 'constructs';

interface Props extends StackProps {
}

export class CognitoUserPoolStack extends Stack {
    constructor(userPoolClientName: string, emailSubject: string, emailBody: string, userPoolName: string, scope: Construct, id: string, private readonly props?: Props) {
        super(scope, id, props);

        const userpool = new cognito.UserPool(this, userPoolName, {
            userPoolName: userPoolName,
            signInAliases: {
                email: true,
            },
            signInCaseSensitive: false,
            selfSignUpEnabled: true,
            autoVerify: {
                email: true,
            },
            userVerification: {
                emailSubject: emailSubject,
                emailBody: emailBody,
                emailStyle: cognito.VerificationEmailStyle.CODE,
            },
            customAttributes: {
                'createdAt': new cognito.DateTimeAttribute(),
            },
            mfa: cognito.Mfa.OPTIONAL,
            mfaSecondFactor: {
                sms: true,
                otp: false,
            },
            passwordPolicy: {
                minLength: 8,
                requireLowercase: true,
                requireUppercase: true,
                requireDigits: true,
                requireSymbols: false,
            },
            accountRecovery: cognito.AccountRecovery.EMAIL_ONLY,
            removalPolicy: RemovalPolicy.DESTROY,
        });

        const appClient = userpool.addClient(userPoolClientName, {
            userPoolClientName: userPoolClientName,
            authFlows: {
                userPassword: true,

            },
            generateSecret: true,
        });
    }
}