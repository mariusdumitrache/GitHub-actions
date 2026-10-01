# demo-app

A small Java 17 / Maven project for practising GitHub Actions with three environments: **TEST**, **UAT** and **PROD**.

## Layout

```
src/main/java/com/example/demo/
  App.java          entry point, prints the active config
  AppConfig.java    loads application-{env}.properties
  Calculator.java   simple logic to test
src/main/resources/
  application-test.properties
  application-uat.properties
  application-prod.properties
src/test/java/com/example/demo/
  CalculatorTest.java
  AppConfigTest.java
```

## Commands

| What | Command |
|---|---|
| Compile | `mvn -B compile` |
| Run tests (TEST env) | `mvn -B test` |
| Run tests against UAT | `mvn -B test -Dapp.env=uat` (or `-P uat`) |
| Build the jar | `mvn -B package` |
| Run the jar | `java -jar target/demo-app-1.0.0-SNAPSHOT.jar` |
| Run it as PROD | `APP_ENV=prod java -jar target/demo-app-1.0.0-SNAPSHOT.jar` |

The environment is picked **at runtime** (`-Dapp.env=...` or the `APP_ENV` env var), so you can build the jar once and promote the same artifact TEST → UAT → PROD.
