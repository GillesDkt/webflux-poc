package net.vince.webfluxpoc.dto.implementations;

import net.vince.webfluxpoc.dto.Plugin;
import org.springframework.stereotype.Service;

@Service
public class EmptyPlugin extends Plugin {

  @Override
  public String name() {
    return "EXAMPLE";
  }
}
