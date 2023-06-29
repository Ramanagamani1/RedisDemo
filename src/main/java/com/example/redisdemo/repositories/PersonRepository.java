package com.example.redisdemo.repositories;

import com.example.redisdemo.models.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public class PersonRepository {

    @Autowired
    RedisTemplate<String, Person> redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    private static final Integer PERSON_VALUE_EXPIRY = 1;

    private static final String PERSON_KEY_PREFIX= "person::";

    private static final String PERSON_LIST_KEY = "person_list";

    private static final String PERSON_HASH_KEY_PREFIX= "person_hash::";

    /* Value ops */

    public void set(Person person) {
        redisTemplate.opsForValue().set(getKey(person.getId()),person, PERSON_VALUE_EXPIRY, TimeUnit.DAYS);
    }

    public String getKey(String personId) {
        return PERSON_KEY_PREFIX + personId;
    }

    public Person getPerson(String personId) {
        return this.redisTemplate.opsForValue().get(getKey(personId));
    }


    public Set<String> getAllKeys() {
        return redisTemplate.keys(PERSON_KEY_PREFIX+"*");
    }

    public Person getByKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /* List Operations */

    public void lpush(Person person) {
        redisTemplate.opsForList().leftPush(PERSON_LIST_KEY,person);
    }

    public void rpush(Person person) {
        redisTemplate.opsForList().rightPush(PERSON_LIST_KEY,person);
    }

    public List<Person> range(int start, int end) {
        return redisTemplate.opsForList().range(PERSON_LIST_KEY,start,end);
    }

    public List<Person> lpop(int count) {
        return redisTemplate.opsForList().leftPop(PERSON_LIST_KEY, count);
    }

    public List<Person> rpop(int count) {
        return redisTemplate.opsForList().rightPop(PERSON_LIST_KEY,count);
    }

    /* Hash Operations */
    public void hmset(Person person) {
        Map map = objectMapper.convertValue(person,Map.class);
        redisTemplate.opsForHash().putAll(getKeyHash(person.getId()),map);
    }

    public String getKeyHash(String personId) {
        return PERSON_HASH_KEY_PREFIX + personId;
    }

    public Person hgetAll(String personId) {
        Map map = redisTemplate.opsForHash().entries(getKeyHash(personId));
        return objectMapper.convertValue(map,Person.class);
    }
}
