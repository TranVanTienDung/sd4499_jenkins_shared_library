#!/usr/bin/env groovy
void call(Map pipelineParams) {

    pipeline {

        agent any

        options {
            disableConcurrentBuilds()
            disableResume()
            timeout(time: 5, unit: 'MINUTES')
        }
        
        stages {
            stage ('Release Backend') {
                when {
                    allOf {
                        // Branch Event: Nornal Flow
                        anyOf {
                            branch 'develop'
                            branch 'PR-*'
                        }
                    }
                }
                steps {
                    script {
                        releaseBackEnd()
                    }
                }
            }
        }

        post {
            cleanup {
                cleanWs()
            }
        }
    }
}
//========================================================================
// node pipeline
// Version: v1.0
// Updated:
//========================================================================
//========================================================================
// Notes:
//
//
//========================================================================
