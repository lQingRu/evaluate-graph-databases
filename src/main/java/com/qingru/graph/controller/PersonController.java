package com.qingru.graph.controller;

import com.qingru.graph.domain.arango.PersonNode;
import com.qingru.graph.domain.neo4j.common.NPersonNode;
import com.qingru.graph.domain.neo4j.optionOne.NPersonNode1;
import com.qingru.graph.domain.neo4j.optionThree.NPersonNode3;
import com.qingru.graph.domain.neo4j.optionTwo.NPersonNode2;
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
        return personService.getNPerson1ById(id);
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

    @PutMapping("/neo4j/person/{id}")
    public NPersonNode updateNPerson(@RequestBody NPersonNode nPersonNode) {
        return personService.updateNPerson(nPersonNode);
    }

    //-------- OPTION 1
    @GetMapping("/neo4j/v1/person/{id}")
    public NPersonNode1 getNPerson1ById(@PathVariable("id") Long id) {
        return personService.getNPerson1ById(id);
    }

    @PostMapping("/neo4j/v1/person")
    public NPersonNode1 createNPerson1(@RequestBody NPersonNode1 nPersonNode1) {
        return personService.createNPerson1(nPersonNode1);
    }

    //-------- OPTION 2
    @GetMapping("/neo4j/v2/person/{id}")
    public NPersonNode2 getNPerson2ById(@PathVariable("id") Long id) {
        return personService.getNPerson2ById(id);
    }

    @PostMapping("/neo4j/v2/person")
    public NPersonNode2 createNPerson2(@RequestBody NPersonNode2 nPersonNode2) {
        return personService.createNPerson2(nPersonNode2);
    }

    //-------- OPTION 3
    @GetMapping("/neo4j/v3/person/{id}")
    public NPersonNode3 getNPerson3ById(@PathVariable("id") Long id) {
        return personService.getNPerson3ById(id);
    }

    @PostMapping("/neo4j/v3/person")
    public NPersonNode3 createNPerson3(@RequestBody NPersonNode3 nPersonNode2) {
        return personService.createNPerson3(nPersonNode2);
    }
}
