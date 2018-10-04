package com.example.demo.configuration;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class InfinispanConfiguration {

    @Bean
    public Cache<String, String> embeddedCacheManager() {
        DefaultCacheManager defaultCacheManager = new DefaultCacheManager();
        defaultCacheManager.defineConfiguration("test-cache",
                new ConfigurationBuilder()
                        .expiration()
                        .wakeUpInterval(10500)
                        .lifespan(11, TimeUnit.SECONDS)
                        .maxIdle(11, TimeUnit.SECONDS)
                        .build()
        );
        return defaultCacheManager.getCache("test-cache");
    }

}
