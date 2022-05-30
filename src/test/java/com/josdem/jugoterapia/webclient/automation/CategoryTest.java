package com.josdem.jugoterapia.webclient.automation;

import com.josdem.jugoterapia.webclient.automation.config.TestDataSource;
import com.josdem.jugoterapia.webclient.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CategoryTest {

  private final CategoryService categoryService;
  private final TestDataSource data;

  @Test
  @DisplayName("it validates categories size")
  void shouldGetCategories(TestInfo testInfo) {
    log.info("Running: {}", testInfo.getDisplayName());
    StepVerifier.create(categoryService.getCategoriesByLanguage("en"))
        .expectNextCount(data.getCategories().size())
        .verifyComplete();
  }
}
