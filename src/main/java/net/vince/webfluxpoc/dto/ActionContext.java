package net.vince.webfluxpoc.dto;

import io.vavr.Tuple1;
import io.vavr.control.Option;
import java.net.http.HttpRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import net.vince.webfluxpoc.domain.Configuration;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class ActionContext {

  Plugin              plugin;

  @With
  Configuration       configuration;

  public static ActionContext forPlugin(final Plugin plugin) {
    return ActionContext.builder()
                        .plugin(plugin)
                        .build();
  }

  public Mono<String> call(){
    return this.getMaybeRequest()
               .map(request -> HttpClient.create()
                                         .baseUrl(request.uri().toASCIIString())
                                         .get()
                                         .response()
                                         .map(response -> getPlugin().interpret(response)))
               .getOrElse(Mono.just("NO CALL"));
  }

  private Option<HttpRequest> getMaybeRequest() {
    return plugin.request(getConfiguration());
  }
}
