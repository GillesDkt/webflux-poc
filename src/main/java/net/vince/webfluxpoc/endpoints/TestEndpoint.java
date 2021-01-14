package net.vince.webfluxpoc.endpoints;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.vince.webfluxpoc.services.PluginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestEndpoint {

  private final PluginService pluginService;

  @ResponseBody
  @GetMapping("/name")
  public Mono<String> appName(){
    return Mono.just("WEBFLUX_POC");
  }

  @ResponseBody
  @GetMapping("/plugin")
  public Mono<String> plugin(@RequestParam String plugin){
    return Mono.just(pluginService.get(plugin).name());
  }

  @ResponseBody
  @GetMapping("/call")
  public Mono<String> call(@RequestParam String plugin){
    return pluginService.call(plugin);
  }

  @ResponseBody
  @GetMapping("/call_with_configuration")
  public Mono<String> callWithConfiguration(@RequestParam String plugin){
    System.out.print('.');
    return pluginService.callWithConfiguration(plugin);
  }

  @SneakyThrows
  @ResponseBody
  @GetMapping("/delay")
  public Mono<String> delay(@RequestParam String delay){
    Thread.sleep(Long.parseLong(delay.replaceAll("\\D", "")));
    System.out.print('.');
    return Mono.just("DELAYED");
  }

}
