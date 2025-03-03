# Running Tests in Parallel

This guide provides step-by-step instructions on how to execute tests in parallel using Maven and a shell script.

## Prerequisites

- Ensure you have Java and Maven installed.
- The required dependencies for test execution should be available in your `pom.xml`.
- Your test framework and project setup should support parallel execution.

## Running a Feature File on a Single Browser

To run a feature file on a specific browser, use the following command:

```sh
mvn test -Dbrowser=safari
```

You can replace `safari` with the desired browser (e.g., `chrome`, `firefox`).

## Running a Feature File on Multiple Browsers in Parallel

To execute tests in parallel across multiple browsers, use the following command:

```sh
sh runner.sh
```

## Customizing Browser Capabilities

- You can modify `runner.sh` to define different browsers for execution.
- Add or change browser capabilities within your test configuration file.
- Ensure your framework supports parallel execution and is configured accordingly.
