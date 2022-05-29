package com.josdem.jugoterapia.automation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class JuiceAutomationApplicationTest {

  @Test
  @DisplayName("it loads the application")
  void shouldLoadApplication(TestInfo testInfo) {
    log.info("Running {}", testInfo.getDisplayName());
    JuiceAutomationApplication.main(new String[] {});
  }
}
