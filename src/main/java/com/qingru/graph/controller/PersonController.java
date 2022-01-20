package com.qingru.graph.controller;

import com.qingru.graph.domain.arango.PersonNode;
import com.qingru.graph.domain.neo4j.NPersonNode;
import com.qingru.graph.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/arango/{id}")
    public PersonNode getPersonById(@PathVariable("id") String id) {
        return personService.getPersonById(id);
    }

    @PostMapping("/arango/person")
    public PersonNode createPerson(@RequestBody PersonNode personNode) {
        return personService.createPerson(personNode);
    }

    @DeleteMapping("/arango/{id}")
    public void deletePerson(@PathVariable("id") Long id) {
        personService.deletePersonByPersonId(Long.toString(id));
    }

    @GetMapping("/neo4j/{id}")
    public NPersonNode getNPersonById(@PathVariable("id") Long id) {
        return personService.getNPersonById(id);
    }

    @PostMapping("/neo4j/person")
    public NPersonNode createNPerson(@RequestBody NPersonNode nPersonNode) {
        return personService.createNPerson(nPersonNode);
    }



}
