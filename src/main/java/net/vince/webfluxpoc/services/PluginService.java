package net.vince.webfluxpoc.services;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import net.vince.webfluxpoc.dto.ActionContext;
import net.vince.webfluxpoc.dto.Plugin;
import net.vince.webfluxpoc.repositories.ConfigurationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
@RequiredArgsConstructor
public class PluginService {

  private final Map<String, Plugin>     plugins = new HashMap<>();

  private final ConfigurationRepository configurationRepository;

  public void register(final Plugin plugin) {
    this.plugins.put(plugin.name(), plugin);
  }

  public Plugin get(String pluginName) {
    return plugins.get(pluginName);
  }

  public Mono<String> call(String pluginName) {

    final var plugin       = plugins.get(pluginName);
    final var maybeRequest = plugin.request();

    return maybeRequest.map(request -> HttpClient.create()
                                                 .baseUrl(request.uri().toASCIIString())
                                                 .get()
                                                 .response()
                                                 .map(plugin::interpret))
                       .getOrElse(Mono.just("NO CALL"));
  }

  public Mono<String> callWithConfiguration(String pluginName) {

    return Mono.fromSupplier(() -> plugins.get(pluginName))
               .map(ActionContext::forPlugin)
               .flatMap(context -> configurationRepository.findById(pluginName)
                                                          .map(context::withConfiguration))
               .flatMap(ActionContext::call);
  }
}
