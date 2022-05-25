Juice Automation
----------------------------------------

This project shows how to test an API using[Junit Jupiter](https://junit.org/junit5/docs/current/user-guide/) and a third-party library [juice-webclient](https://github.com/josdem/juice-webclient)

#### To test the project with Gradle

```bash
export ARTIFACTORY_USER=${username}
export ARTIFACTORY_PASSWORD=${password}
gradle test
```

where:

- `${username}` Is artifactory username
- `${password}` Is artifactory password

### To run a single test with Gradle

```bash
gradle test --tests ${testName}
```

where:

- `${testName}` is the test name you want to execute

#### Read this as reference:

* https://josdem.io/techtalk/spring/spring_boot_webclient/
* https://josdem.io/techtalk/spring/spring_webflux_client/
* https://josdem.io/techtalk/spring/spring_webflux_artifactory_library/