package com.josdem.jugoterapia.webclient.automation.config;

import com.josdem.jugoterapia.webclient.model.Category;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("data")
public class TestDataSource {
  private List<Category> categories;
}
