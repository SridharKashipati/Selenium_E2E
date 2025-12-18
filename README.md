# Selenium E2E Test Automation Framework

Production-quality automation framework for UI and API testing.

## Project Structure

```
selenium-e2e/
├── src/main/java/
│   ├── pages/LoginPage.java       # Page Object
│   ├── api/ApiClient.java         # API client
│   └── utils/
│       ├── DriverFactory.java     # WebDriver factory
│       ├── WaitUtils.java         # Wait utilities
│       ├── DataReader.java        # JSON data reader
│       └── Config.java            # Configuration
├── src/test/java/tests/
│   ├── BaseTest.java              # Setup/teardown
│   ├── LoginTest.java             # 2 UI tests
│   └── ApiTest.java               # 2 API tests
├── data/
│   ├── loginData.json             # UI test data
│   └── apiTestData.csv            # API test data
├── reports/                       # Generated reports
│   ├── allure-report/             # Allure HTML report
│   ├── allure-results/            # Allure raw results
│   └── surefire/                  # TestNG reports
├── .github/workflows/ci.yml       # CI pipeline
├── DESIGN.md                      # Architecture
└── RETROSPECTIVE.md               # Next steps
```

## Prerequisites

- Java 17+
- Maven 3.8+
- Chrome browser (for UI tests)

## Run Tests Locally

```bash
# Run all tests
mvn clean test

# Run API tests only
mvn test -Dtest=tests.ApiTest

# Run UI tests only
mvn test -Dtest=tests.LoginTest
```

## Generate Reports

```bash
# Generate Allure report
mvn allure:report

# View report in browser
mvn allure:serve

# Clean all (target + reports)
mvn clean
```

Report location: `reports/allure-report/index.html`

## Run in CI

Tests run automatically on GitHub Actions:

- Trigger: Push to `main` branch
- Pipeline: Build → Test → Report
- See: `.github/workflows/ci.yml`

## Environment Variables (Optional)

```bash
export TEST_USERNAME=tomsmith
export TEST_PASSWORD=SuperSecretPassword!
```

## Tests Implemented

### UI Tests (LoginTest.java) - Data-driven from JSON

| Test                | Description                                  |
| ------------------- | -------------------------------------------- |
| testSuccessfulLogin | Valid credentials from loginData.json        |
| testFailedLogin     | Invalid credentials, validates error message |

### API Tests (ApiTest.java)

| Test                | Description                              |
| ------------------- | ---------------------------------------- |
| testGetAllPosts     | GET /posts - validates 200, body, schema |
| testNonExistentPost | GET /posts/99999 - validates 404         |

## Tech Stack

- **Java 17** - Language
- **Selenium 4.16.1** - UI automation
- **WebDriverManager 5.6.3** - WebDriver management
- **REST Assured 5.4.0** - API testing
- **TestNG 7.9.0** - Test framework
- **Allure 2.25.0** - Reporting
- **SLF4J 2.0.9** - Logging
- **Jackson 2.16.1** - JSON serialization/deserialization
- **Maven** - Build tool

## Test Run Evidence

The `reports/` folder contains all test evidence:

- `reports/allure-report/` - Allure HTML report with test results
- `reports/allure-results/` - Raw Allure results (JSON)
- `reports/surefire/` - TestNG HTML reports

### Sample Console Output with Logging

```
[INFO] Running tests.ApiTest
[main] INFO api.ApiClient - API Base URI set to: https://jsonplaceholder.typicode.com
[main] INFO api.ApiClient - GET request: /posts
[main] INFO api.ApiClient - GET response status: 200
[main] INFO api.ApiClient - GET request: /posts/99999
[main] INFO api.ApiClient - GET response status: 404
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

## Sensitive Data Handling

- **Local**: Default credentials in `data/loginData.json` (demo only)
- **CI**: GitHub Actions secrets via environment variables
- **Production**: Use vault/secrets manager (see DESIGN.md)
