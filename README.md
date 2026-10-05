            # Google Cloud Calculator Automation Framework

This project is an automated QA test framework designed to validate the Google Cloud Pricing Calculator and ensure that pricing logic, user interactions, and critical business flows work correctly in real browser conditions.

The framework covers following scenarios:
- comparing calculated costs between the pricing form (CalculatorPricingPage) and the detailed view cost form (DetailedViewPage)
- validation of the email-based cost sharing flow.

## Key QA aspects
- Functional UI testing of the Google Cloud Calculator
- Implementation of Page Object pattern and Business Object Model
- Regression and smoke test coverage
- Cross-environment configuration support (`qa`, `dev`)
- Multibrowser support (Chrome, FireFox, Edge)
- CI/CD execution through Jenkins
- Detailed reporting with Allure and ReportPortal

## Tech stack
- Java 17
- Maven
- Selenium WebDriver
- TestNG
- Jenkins
- Allure / ReportPortal

## Example run
```bash
mvn clean test -Denv=qa -Dbrowser=chrome -Dheadless=true -DsuiteXmlFile=src/test/resources/smoke-suite.xml
```

The project reflects practical experience in automated testing for web applications, with emphasis on test reliability, maintainability, and reporting.