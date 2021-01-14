package net.vince.webfluxpoc.services;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;
import net.vince.webfluxpoc.dto.Plugin;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
public class PluginService {

  private final Map<String, Plugin> plugins = new HashMap<>();

  public void register(final Plugin plugin) {
    this.plugins.put(plugin.name(), plugin);
  }

  public Plugin get(String pluginName) {
    return plugins.get(pluginName);
  }

  public Mono<String> call(String pluginName) {

    return Mono.fromSupplier(() -> plugins.get(pluginName))
               .map(plugin -> Tuple.of(plugin, plugin.request()))
               .map(pluginWithRequest -> pluginWithRequest.map(this::maybeCall))
               .flatMap(Tuple2::_2);
  }

  private Tuple2<Plugin, Mono<String>> maybeCall(final Plugin plugin, final Option<HttpRequest> maybeRequest) {
    return Tuple.of(plugin,
                    maybeRequest.map(request -> HttpClient.create()
                                                          .baseUrl(request.uri().toASCIIString())
                                                          .get()
                                                          .response()
                                                          .map(plugin::interpret))
                                .getOrElse(Mono.just("NO CALL")));
  }
}
