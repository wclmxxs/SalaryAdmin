package cwall.club.common.Config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Configuration
public class RedisConfig {
    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    private void init(){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(getSerializerObject());
        redisTemplate.setHashValueSerializer(getSerializerObject());
    }

    private Jackson2JsonRedisSerializer<Object> getSerializerObject(){
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper mapper= new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT, JsonTypeInfo.As.WRAPPER_ARRAY);
        serializer.setObjectMapper(mapper);
        return serializer;
    }

    public Object getValue(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void setExValue(String key, Object value,int time){
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(time));
    }

    public void removeKey(String key){
        redisTemplate.delete(key);
    }
}
