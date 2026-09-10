pipeline {

    agent { label 'agent-tests' }

    parameters {
        choice(
            name: 'ENVIRONMENT',
            choices: ['qa', 'dev'],
            description: 'Выберите тестовое окружение (загрузит соответствующий .properties файл)'
        )
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox', 'edge'],
            description: 'Выберите браузер для тестирования'
        )
        booleanParam(
            name: 'HEADLESS_MODE',
            defaultValue: true,
            description: 'Запускать браузер в скрытом режиме (обязательно для Jenkins без GUI)'
        )
        choice(
            name: 'SUITE',
            choices: ['src/test/resources/regression-suite.xml', 'src/test/resources/smoke-suite.xml'],
            description: 'Выберите TestNG XML сьют для запуска'
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