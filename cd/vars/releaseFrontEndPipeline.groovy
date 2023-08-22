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
            stage ('Release Frontend') {
                when {
                    allOf {
                        // Branch Event: Nornal Flow
                        anyOf {
                            branch 'develop'
                        }
                    }
                }
                steps {
                    script {
                        releaseFrontEnd()
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
