package com.josdem.jugoterapia.webclient.automation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CategoriesByIdTest {

  @Test
  @DisplayName("It filters by category id")
  void shouldSelectCategoriesById(TestInfo testInfo) {
    log.info("Running {}", testInfo.getDisplayName());
  }
}
