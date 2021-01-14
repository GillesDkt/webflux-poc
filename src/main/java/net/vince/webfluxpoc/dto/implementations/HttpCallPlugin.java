package net.vince.webfluxpoc.dto.implementations;

import io.vavr.control.Option;
import java.net.URI;
import java.net.http.HttpRequest;
import lombok.SneakyThrows;
import net.vince.webfluxpoc.dto.Plugin;
import org.springframework.stereotype.Service;
import reactor.netty.http.client.HttpClientResponse;

@Service
public class HttpCallPlugin extends Plugin {

  @Override
  public String name() {
    return "HTTP";
  }

  @Override
  public Option<HttpRequest> request() {
    return Option.some(HttpRequest.newBuilder()
                                  .GET()
                                  .uri(URI.create("https://webhook.site/b71a1fdd-2161-446a-8fd5-01ee0feff2ee"))
                                  .build());
  }

  @Override
  public String interpret(final HttpClientResponse response) {
    return response.responseHeaders().get("Date");
  }
}
