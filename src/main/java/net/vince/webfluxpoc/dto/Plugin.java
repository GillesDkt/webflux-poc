package net.vince.webfluxpoc.dto;

import io.vavr.control.Option;
import java.net.http.HttpRequest;
import javax.annotation.PostConstruct;
import net.vince.webfluxpoc.domain.Configuration;
import net.vince.webfluxpoc.services.PluginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.netty.http.client.HttpClientResponse;

@Service
public abstract class Plugin {

  @Autowired
  private PluginService pluginService;

  @PostConstruct
  protected void init(){
    pluginService.register(this);
  }

  public abstract String name();

  public Option<HttpRequest> request(){
    return Option.none();
  }

  public String interpret(HttpClientResponse response) {
    return "NO CALL";
  }

  public Option<HttpRequest> request(Configuration ignored) {
    return Option.none();
  }
}
