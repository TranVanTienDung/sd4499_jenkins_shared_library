#!/usr/bin/env groovy
void call() {
    String name = "frontend"
    String buildFolder = "frontend"
    String demoRegistry = "386785551208.dkr.ecr.us-east-2.amazonaws.com"
    String awsRegion = "us-east-2"
    String eksName = "practical-devops-sd4499-eks"
    String ecrRegistryUrl = "https://386785551208.dkr.ecr.us-east-2.amazonaws.com"
    String awsCredential = 'aws-credentials'

//========================================================================
//========================================================================

//========================================================================
//========================================================================
    stage ('Prepare Package') {
        script {
            writeFile file: '.ci/service/deployment.yml', text: libraryResource('deploy/eks/service/deployment.yml')
        }
    }

    stage ("Deploy Frontend To K8S") {
        withAWS(credentials: "${awsCredential}", region: "${awsRegion}") {
            sh "aws ecr get-login-password --region ${awsRegion} | docker login --username AWS --password-stdin ${demoRegistry}"
            sh "export registry=${demoRegistry}; export appname=${name}; export tag=latest; \
            envsubst < .ci/service/deployment.yml > deployment.yml"
            sh "aws eks --region ${awsRegion} update-kubeconfig --name ${eksName}"
            sh "kubectl apply -f deployment.yml"
        }
    }
}

//========================================================================
// node CI
// Version: v1.0
// Updated:
//========================================================================
//========================================================================
// Notes:
//
//
//========================================================================