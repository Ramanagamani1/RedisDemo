package com.example.redisdemo.services;

import com.example.redisdemo.models.Person;
import com.example.redisdemo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    /* Value ops */

    public void createPerson(Person person) {
        personRepository.set(person);
    }

    public Person getPerson(String personId) {
        return personRepository.getPerson(personId);
    }

    public List<Person> getPeople() {
        Set<String> keys = personRepository.getAllKeys();
        return keys.stream()
                .map(k -> personRepository.getByKey(k))
                .collect(Collectors.toList());
    }

    /* List Operations */

    public void lpush(Person person) {
        personRepository.lpush(person);
    }

    public void rpush(Person person) {
        personRepository.rpush(person);
    }

    public List<Person> range(int start, int end) {
       return personRepository.range(start,end);
    }

    public List<Person> rpop(int count) {
        return personRepository.rpop(count);
    }

    public List<Person> lpop(int count) {
        return personRepository.lpop(count);
    }

    /* Hash Operations */
    public void setPersonInHash(Person person) {
        personRepository.hmset(person);
    }

    public Person getPersonFromHash(String personId) {
        return personRepository.hgetAll(personId);
    }
}
