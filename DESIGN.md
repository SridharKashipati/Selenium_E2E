# Framework Design Document

## Architecture

```
┌────────────────────────────────────────────────────────────-─┐
│                      TEST LAYER                              │
│  ┌─────────────────┐           ┌─────────────────┐           │
│  │   LoginTest     │           │    ApiTest      │           │
│  │  (2 UI tests)   │           │  (2 API tests)  │           │
│  └────────┬────────┘           └────────┬────────┘           │
│           │                             │                    │
│           └──────────┬──────────────────┘                    │
│                      ▼                                       │
│              ┌───────────────┐                               │
│              │   BaseTest    │                               │
│              │ (setup/teardown)                              │
│              └───────────────┘                               │
└──────────────────────┬───────────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────────┐
│                   FRAMEWORK LAYER                           │
│                                                             │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │  LoginPage  │  │  ApiClient  │  │      UTILS          │  │
│  │(Page Object)│  │ (REST API)  │  │ DriverFactory       │  │
│  └─────────────┘  └─────────────┘  │ WaitUtils           │  │
│                                    │ DataReader          │  │
│                                    │ Config              │  │
│                                    └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                       │
┌──────────────────────▼────────────────────────────────────────────────────────────────────────┐
│                                     EXTERNAL LIBRARIES                                        │
│  Selenium 4.16.1 │ REST Assured 5.4.0 │ TestNG 7.9.0 │ Allure 2.25.0 │ WebDriverManager 5.6.3 │
└───────────────────────────────────────────────────────────────────────────────────────────────┘
```

## Folder Structure

```
src/
├── main/java/
│   ├── pages/
│   │   └── LoginPage.java      # Page Object with locators
│   ├── api/
│   │   └── ApiClient.java      # REST API client
│   └── utils/
│       ├── DriverFactory.java  # Creates WebDriver
│       ├── WaitUtils.java      # Explicit waits
│       ├── DataReader.java     # Reads JSON test data
│       └── Config.java         # URLs, credentials, timeouts
├── test/java/tests/
│   ├── BaseTest.java           # Common setup/teardown
│   ├── LoginTest.java          # 2 UI tests (data-driven)
│   └── ApiTest.java            # 2 API tests
data/
├── loginData.json              # UI test data
└── apiTestData.csv             # API test data
reports/
├── allure-report/              # Allure HTML report
├── allure-results/             # Allure raw results
└── surefire/                   # TestNG reports
```

## Tech Choices

| Technology       | Version | Why                                      |
| ---------------- | ------- | ---------------------------------------- |
| Java 17          | LTS     | Enterprise standard, strong typing       |
| Selenium         | 4.16.1  | Industry standard for web automation     |
| WebDriverManager | 5.6.3   | WebDriver management                     |
| REST Assured     | 5.4.0   | Fluent API for REST testing              |
| TestNG           | 7.9.0   | Better than JUnit for parallel execution |
| Allure           | 2.25.0  | Interactive HTML reports                 |
| SLF4J            | 2.0.9   | Logging for debugging and audit          |
| Jackson          | 2.16.1  | JSON parsing for data-driven tests       |

## Design Patterns

### Page Object Model

```java
public class LoginPage {
    private By usernameInput = By.id("username");

    public void login(String user, String pass) {
        wait.waitForVisible(usernameInput).sendKeys(user);
    }
}
```

**Why**: Separates locators from tests, easy maintenance.

### Factory Pattern

```java
public class DriverFactory {
    public static WebDriver createDriver() {
        return new ChromeDriver(options);
    }
}
```

**Why**: Centralized driver creation, easy to add browsers.

## Scalability

| Need              | Solution                         |
| ----------------- | -------------------------------- |
| More pages        | Add new Page Objects in `pages/` |
| More browsers     | Extend DriverFactory with switch |
| Parallel tests    | TestNG `parallel="methods"`      |
| More environments | Extend Config with env profiles  |

## Flaky Test Handling

1. **Explicit Waits**: WaitUtils ensures elements are ready
2. **Retry**: TestNG IRetryAnalyzer (can be added)
3. **Headless Mode**: Reduces UI flakiness in CI

## Sensitive Data Handling

```java
// Config.java reads from environment variables
public static final String PASSWORD = getEnv("TEST_PASSWORD", "default");
```

- **Local**: Use defaults for demo
- **CI**: Set secrets in GitHub Actions
- **Production**: Use vault/secrets manager
