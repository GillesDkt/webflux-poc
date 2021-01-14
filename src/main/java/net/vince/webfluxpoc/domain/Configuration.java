package net.vince.webfluxpoc.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Configuration {

  @Id
  private String plugin;

  private String url;

}
