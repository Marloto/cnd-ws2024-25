package de.thi.informatik.cnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.net.InetAddress;

@SpringBootApplication
@RestController
public class StressTestApplication {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    private boolean healthStatus = true; 

    public static void main(String[] args) {
        SpringApplication.run(StressTestApplication.class, args);
    }

    @GetMapping("/")
    public Map<String, Object> base() throws Exception {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ok");
        response.put("hostname", InetAddress.getLocalHost().getHostName());
        response.put("timestamp", new Date());
        return response;
    }

    @GetMapping("/stress/cpu/{seconds}")
    public Map<String, Object> stressCpu(@PathVariable int seconds) throws Exception {
        long start = System.currentTimeMillis();
        double value = 0;
        while (System.currentTimeMillis() - start < seconds * 1000) {
            value += Math.random() * Math.random();
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", String.format("CPU stress test completed after %d seconds", seconds));
        response.put("hostname", InetAddress.getLocalHost().getHostName());
        return response;
    }

    @GetMapping("/counter")
    public Map<String, Object> counter() throws Exception {
        try{
            Long count = redisTemplate.opsForValue().increment("visitor_count");
            
            Map<String, Object> response = new HashMap<>();
            response.put("count", count);
            response.put("hostname", InetAddress.getLocalHost().getHostName());
            return response;
        } catch (Exception e) {
            LettuceConnectionFactory cf = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();

            System.out.println("Redis Client Configuration:");
            System.out.println("Host: " + cf.getHostName());
            System.out.println("Port: " + cf.getPort());
            System.out.println("Database: " + cf.getDatabase());
            System.out.println("Client: " + cf.getClientConfiguration());
            System.out.println("Redis Connection Failed:");
            System.out.println("Host: " + System.getenv().getOrDefault("REDIS_HOST", "localhost"));
            System.out.println("Port: " + System.getenv().getOrDefault("REDIS_PORT", "6379"));
            throw e;
        }
    }

    @GetMapping("/hello")
    public Map<String, Object> hello() throws Exception {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello, World!");
        response.put("hostname", InetAddress.getLocalHost().getHostName());
        return response;
    }

    @PostMapping("/log")
    public Map<String, Object> log(@RequestBody Map<String, Object> data) throws Exception {
        data.put("timestamp", new Date());
        Map<String, Object> saved = mongoTemplate.save(data, "logs");
        
        Map<String, Object> response = new HashMap<>();
        response.put("inserted", saved.get("_id"));
        response.put("hostname", InetAddress.getLocalHost().getHostName());
        return response;
    }

    @GetMapping("/crash")
    public Map<String, Object> crash() throws Exception {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Crashing in 1 second...");
        response.put("hostname", InetAddress.getLocalHost().getHostName());
        
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.exit(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        
        return response;
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        if (healthStatus) {
            response.put("status", "ok");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }
    }

    @GetMapping("/toggle-health")
    public ResponseEntity<?> toggleHealth() {
        healthStatus = !healthStatus;
        return ResponseEntity.noContent().build();
    }
}