pipeline {

    agent { label 'agent-tests' }

    parameters {
        choice(
            name: 'ENVIRONMENT',
            choices: ['qa', 'dev'],
            description: 'Choose test environment'
        )
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox', 'edge'],
            description: 'Choose the browser for testing'
        )
        booleanParam(
            name: 'HEADLESS_MODE',
            defaultValue: true,
            description: 'Start browser in healless mode'
        )
        choice(
            name: 'SUITE',
            choices: ['src/test/resources/regression-suite.xml', 'src/test/resources/smoke-suite.xml'],
            description: 'Choose TestNG XML suite file'
        )
    }

    tools {

        jdk 'jdk17'
        maven '3.8.5'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com'
            }
        }

        stage('Run Tests') {
            steps {
                bat "mvn clean test -Denv=${params.ENVIRONMENT} -Dbrowser=${params.BROWSER} -Dheadless=${params.HEADLESS_MODE} -DsuiteXmlFile=${params.SUITE} -DtestFailureIgnore=true"
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}