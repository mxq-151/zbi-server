package org.zbi.server.rest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class RedisTokenStoreConfig {

	private final static Logger logger = LoggerFactory.getLogger(RedisTokenStoreConfig.class);

	@Value("${spring.redis.token.host}")
	private String tokenHost;

	@Value("${spring.redis.token.port}")
	private int tokenPort;

	@Value("${spring.redis.token.password:NULL}")
	private String tokenPassword;

	@Value("${spring.redis.token.db:0}")
	private int db;

	@Bean("redisTokenStore")
	public RedisTokenStore getTokenStore(@Qualifier("redisTokenFactory") RedisConnectionFactory connectionFactory) {

		RedisTokenStore redisTokenStore = new RedisTokenStore(connectionFactory);
		return redisTokenStore;
	}

	@Bean("redisAuthTemplate")
	public RedisTemplate<String, String> redisTemplateObject(
			@Qualifier("redisTokenFactory") RedisConnectionFactory connectionFactory) throws Exception {
		RedisTemplate<String, String> redisTemplateObject = new RedisTemplate<String, String>();
		initRedisTemplate(redisTemplateObject,connectionFactory);
		return redisTemplateObject;
	}
	
	private void initRedisTemplate(RedisTemplate<String,String> redisTemplate, RedisConnectionFactory factory) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
    }

	@Bean
	public RedisMessageListenerContainer getRedisMessageListenerContainer(
			@Qualifier("redisTokenFactory") RedisConnectionFactory connectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		return container;
	}

	@Bean("redisTokenFactory")
	public RedisConnectionFactory createRedisConnectionFactory() {

		logger.info("create redis token store in host:{},port:{},password:{},db:{}", tokenHost, tokenPort,
				tokenPassword, this.db);
		return this.connectionFactory(tokenHost, tokenPort, tokenPassword, 20, 1024, 100000, 0, this.db);

	}

	public RedisConnectionFactory connectionFactory(String host, int port, String password, int maxIdle, int maxTotal,
			long maxWaitMillis, int index, int db) {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
		config.setHostName(host);
		config.setPort(port);
		if (password != null && !"NULL".equals(password)) {
			config.setPassword(password);
		}
		config.setDatabase(db);

		return new JedisConnectionFactory(config);
	}

}
