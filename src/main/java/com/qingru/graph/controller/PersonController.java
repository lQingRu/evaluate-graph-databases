package com.qingru.graph.controller;

import com.qingru.graph.domain.arango.PersonNode;
import com.qingru.graph.domain.neo4j.common.NPersonNode;
import com.qingru.graph.domain.neo4j.optionFive.NPersonNode5;
import com.qingru.graph.domain.neo4j.optionFive.NPersonNode5List;
import com.qingru.graph.domain.neo4j.optionFour.NPersonNode4;
import com.qingru.graph.domain.neo4j.optionFour.NPersonNode4List;
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
    public NPersonNode3 createNPerson3(@RequestBody NPersonNode3 nPersonNode3) {
        return personService.createNPerson3(nPersonNode3);
    }

    //-------- OPTION 4
    @GetMapping("/neo4j/v4.1/person/{id}")
    public NPersonNode4 getNPerson4ById(@PathVariable("id") Long id) {
        return personService.getNPerson4ById(id);
    }

    @PostMapping("/neo4j/v4.1/person")
    public NPersonNode4 createNPerson4(@RequestBody NPersonNode4 nPersonNode4) {
        return personService.createNPerson4(nPersonNode4);
    }

    @GetMapping("/neo4j/v4.2/person/{id}")
    public NPersonNode4List getNPerson4ListById(@PathVariable("id") Long id) {
        return personService.getNPerson4ListById(id);
    }

    @PostMapping("/neo4j/v4.2/person")
    public NPersonNode4List createNPerson4List(@RequestBody NPersonNode4List nPersonNode4List) {
        return personService.createNPerson4List(nPersonNode4List);
    }

    //-------- OPTION 5
    @GetMapping("/neo4j/v5.1/person/{id}")
    public NPersonNode5 getNPerson5ById(@PathVariable("id") Long id) {
        return personService.getNPerson5ById(id);
    }

    @PostMapping("/neo4j/v5.1/person")
    public NPersonNode5 createNPerson5(@RequestBody NPersonNode5 nPersonNode5) {
        return personService.createNPerson5List(nPersonNode5);
    }

    @GetMapping("/neo4j/v5.2/person/{id}")
    public NPersonNode5List getNPerson5ListById(@PathVariable("id") Long id) {
        return personService.getNPerson5ListById(id);
    }
    
    @PostMapping("/neo4j/v5.2/person")
    public NPersonNode5List createNPerson5List(@RequestBody NPersonNode5List nPersonNode5List) {
        return personService.createNPerson5List(nPersonNode5List);
    }
}
