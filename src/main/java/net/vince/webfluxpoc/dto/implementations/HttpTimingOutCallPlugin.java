package net.vince.webfluxpoc.dto.implementations;

import io.vavr.control.Option;
import java.net.URI;
import java.net.http.HttpRequest;
import net.vince.webfluxpoc.domain.Configuration;
import net.vince.webfluxpoc.dto.Plugin;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import reactor.netty.http.client.HttpClientResponse;

@Service
public class HttpTimingOutCallPlugin extends Plugin {

  @Override
  public String name() {
    return "HTTP_TIMING_OUT";
  }

  @Override
  public Option<HttpRequest> request() {
    return Option.some(HttpRequest.newBuilder()
                                  .GET()
                                  .uri(URI.create("https://webhook.site/fcad72d0-728c-4d45-ac5b-290045f667cf"))
                                  .build());
  }

  @Override
  public Option<HttpRequest> request(Configuration configuration) {
    return Option.some(HttpRequest.newBuilder()
                                  .GET()
                                  .uri(URI.create(configuration.getUrl()))
                                  .build());
  }

  @Override
  public String interpret(final HttpClientResponse response) {
    return response.responseHeaders().get(HttpHeaders.CONTENT_TYPE);
  }
}
