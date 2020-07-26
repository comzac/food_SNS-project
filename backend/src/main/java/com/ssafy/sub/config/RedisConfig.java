package com.ssafy.sub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.ssafy.sub.dto.Token;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableCaching
@Configuration
public class RedisConfig {
	
	@Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.database}")
    private int redisDatabase;

	
	@Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
        lettuceConnectionFactory.setHostName(redisHost);
        lettuceConnectionFactory.setPort(redisPort);
        lettuceConnectionFactory.setDatabase(redisDatabase);
//        lettuceConnectionFactory.setUsePool(true);
        return lettuceConnectionFactory;
    }
	
//	@Bean
//	public LettuceConnectionFactory lettuceConnectionFactory() {
//		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
//		return lettuceConnectionFactory;
//	}
	
//	@Bean
//    public JedisConnectionFactory jedisConnectionFactory() {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.setHostName(redisHost);
//        jedisConnectionFactory.setPort(redisPort);
//        jedisConnectionFactory.setDatabase(redisDatabase);
//        jedisConnectionFactory.setUsePool(true);
//        return jedisConnectionFactory;
//    }
	
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //객체를 json 형태로 깨지지 않고 받기 위한 직렬화 작업
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Token.class));
        return redisTemplate;
    }

//    @Bean(name = "cacheManager")
//    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//
//        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
//                .disableCachingNullValues() // null value 캐시안함
//                .entryTtl(Duration.ofSeconds(CacheKey.DEFAULT_EXPIRE_SEC)) // 캐시의 기본 유효시간 설정
//                .computePrefixWith(CacheKeyPrefix.simple())
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())); // redis 캐시 데이터 저장방식을 StringSeriallizer로 지정
//
//        // 캐시키별 default 유효시간 설정
//        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
//        cacheConfigurations.put(CacheKey.USER, RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofSeconds(CacheKey.USER_EXPIRE_SEC)));
//        cacheConfigurations.put(CacheKey.BOARD, RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofSeconds(CacheKey.BOARD_EXPIRE_SEC)));
//        cacheConfigurations.put(CacheKey.POST, RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofSeconds(CacheKey.POST_EXPIRE_SEC)));
//        cacheConfigurations.put(CacheKey.POSTS, RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofSeconds(CacheKey.POST_EXPIRE_SEC)));
//
//        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory).cacheDefaults(configuration)
//                .withInitialCacheConfigurations(cacheConfigurations).build();
//    }
}