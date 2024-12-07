package VTTPday17.homework.repository;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import VTTPday17.homework.model.Weather;
import jakarta.json.JsonObject;

@Repository
public class WeatherRepository {
    @Autowired @Qualifier("redis-object")
    private RedisTemplate<String, Object> template;

    
    public void save(String cityName, String json){

        HashOperations<String, String, Object> hashOps = template.opsForHash();

        hashOps.put(cityName, cityName, json);
        
        // Set an expiration time for the cache data (in seconds)
        template.expire(cityName, Duration.ofSeconds(60));  // TTL in seconds
    }

    public String get(String cityName){
        HashOperations<String, String, Object> hashOps = template.opsForHash();

        return (String) hashOps.get(cityName, cityName); //returns JsonString
    }




}
