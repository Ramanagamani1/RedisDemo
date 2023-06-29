package com.example.redisdemo.controllers;

import com.example.redisdemo.dtos.PersonCreateRequest;
import com.example.redisdemo.models.Person;
import com.example.redisdemo.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    /*  Value ops */

    @Autowired
    PersonService personService;

    @PostMapping("/")
    public void createPerson(@RequestBody @Valid PersonCreateRequest request) {
        personService.createPerson(request.to());
    }

    @GetMapping("/")
    public Person getPerson(@RequestParam("id") String personId) {
        return personService.getPerson(personId);
    }

    @GetMapping("/all")
    public List<Person> getPeople() {
        return personService.getPeople();
    }

    /* List Operations */

    @PostMapping("/lpush")
    public void lpush(@RequestBody @Valid PersonCreateRequest request) {
        personService.lpush(request.to());
    }

    @PostMapping("/rpush")
    public void rpush(@RequestBody @Valid PersonCreateRequest request) {
        personService.rpush(request.to());
    }

    @DeleteMapping("/lpop")
    public List<Person> lpop(@RequestParam(value = "count", required = false,defaultValue = "1") int count) {
        return personService.lpop(count);
    }

    @DeleteMapping("/rpush")
    public List<Person> rpop(@RequestParam(value = "count", required = false,defaultValue = "1") int count) {
        return personService.rpop(count);
    }

    @GetMapping("/range")
    public List<Person> range(@RequestParam(value = "start",required = false,defaultValue = "1") int start,
                              @RequestParam(value = "end",required = false,defaultValue = "1") int end){

        return personService.range(start,end);

    }

    /* Hash Operations */

    @PostMapping("/hash")
    public void createPersonInHash(@RequestBody @Valid PersonCreateRequest request) {
        personService.setPersonInHash(request.to());
    }

    @GetMapping("/hash/{id}")
    public Person getPersonFromHash(@PathVariable("id") String personId) {
        return personService.getPersonFromHash(personId);
    }
}
