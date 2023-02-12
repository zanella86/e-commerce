package br.com.fiap.sportconnection.ecommerce.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@EnableCaching
public class RedisConfig {

    private final Environment env;

    public RedisConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public LettuceConnectionFactory regisConnectionFactory() {
        var redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(env.getProperty("spring.redis.host"));
        redisStandaloneConfiguration.setPort(Integer.parseInt(env.getProperty("spring.redis.port")));
        redisStandaloneConfiguration.setPassword(RedisPassword.of(env.getProperty("spring.redis.password")));
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        var redisCacheManager = RedisCacheManager.create(regisConnectionFactory());
        redisCacheManager.setTransactionAware(true);
        return redisCacheManager;
    }

}
