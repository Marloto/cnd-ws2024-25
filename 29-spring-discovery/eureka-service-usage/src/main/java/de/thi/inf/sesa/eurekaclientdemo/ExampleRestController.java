package de.thi.inf.sesa.eurekaclientdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ExampleRestController {
    @Autowired
    private DiscoveryClient discovery;

    @Value("${example.name:cnd-service-example}")
    private String targetService;
    
    @GetMapping("/hello")
    public List<String> hello() {
        return discovery.getInstances(targetService).stream()
            .map(e -> e.toString())
            .collect(Collectors.toList());
    }
}
