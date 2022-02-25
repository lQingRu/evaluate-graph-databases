package com.qingru.graph.controller;

import com.qingru.graph.domain.arango.PersonNode;
import com.qingru.graph.domain.arango.PersonRelationshipEdge;
import com.qingru.graph.domain.neo4j.common.NRelationshipData;
import com.qingru.graph.domain.neo4j.optionFive.NPersonNode5;
import com.qingru.graph.domain.neo4j.optionFive.NPersonNode5List;
import com.qingru.graph.domain.neo4j.optionFour.NPersonNode4;
import com.qingru.graph.domain.neo4j.optionFour.NPersonNode4List;
import com.qingru.graph.domain.neo4j.optionOne.NPersonNode1;
import com.qingru.graph.domain.neo4j.optionOne.NRelationshipData1;
import com.qingru.graph.domain.neo4j.optionThree.NPersonNode3;
import com.qingru.graph.domain.neo4j.optionThree.NRelationshipData3;
import com.qingru.graph.domain.neo4j.optionTwo.NPersonNode2;
import com.qingru.graph.service.PersonService;
import com.qingru.graph.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relationship")
public class RelationshipController {
    @Autowired
    RelationshipService relationshipService;
    @Autowired
    PersonService personService;

    @GetMapping("/arango/person/{id}")
    public List<PersonRelationshipEdge> getRelationshipsByPersonId(@PathVariable("id") String id,
            @Nullable @RequestParam("degree") Integer degree) {
        PersonNode personNode = personService.getPersonById(id);
        List<PersonRelationshipEdge> edges =
                relationshipService.getPersonRelationshipsByPerson(personNode, degree);
        return edges;
    }

    @PostMapping("/arango")
    public PersonRelationshipEdge createRelationship(
            @RequestBody NRelationshipData NRelationshipData) {
        return relationshipService.createPersonRelationship(NRelationshipData);
    }

    @DeleteMapping("/arango/relationship/{id}")
    public void removeRelationshipByEdgeId(@PathVariable("id") String id) {
        relationshipService.deletePersonRelationshipByEdgeId(id);
    }

    //-------- OPTION 1
    @PostMapping("/neo4j/v1/relationship")
    public NPersonNode1 createNRelationship1(@RequestBody NRelationshipData1 nRelationshipData1) {
        return relationshipService.createPersonRelationship1(nRelationshipData1);
    }

    // This API will also return relationship because we assume that whenever we want to retrieve
    // person object, we want to retrieve together with the relationship
    @GetMapping("/neo4j/v1/person/{id}")
    public List<? extends Object> getNPerson1AndRelationshipsById(@PathVariable("id") Long id,
            @Nullable @RequestParam("degree") Integer degree) {
        // if degree is not specified, only return the direct relationship
        if (degree == null || degree == 1) {
            return relationshipService.getPersonsAndDirectRelationshipByPersonId(id);
        } else {
            return relationshipService.getPersonsAndRelationshipWithDegreeByPersonId(id, degree);
        }
    }

    //-------- OPTION 2
    @PostMapping("/neo4j/v2/relationship")
    public NPersonNode2 createNRelationship2(@RequestBody NRelationshipData nRelationshipData) {
        return relationshipService.createNPersonRelationship2(nRelationshipData);
    }

    @GetMapping("/neo4j/v2/person/{id}")
    public NPersonNode2 getNRelationshipsByPersonId2(@PathVariable("id") Long id,
            @Nullable @RequestParam("degree") Integer degree) {
        return relationshipService.getNPersonRelationships2ByPersonId(id, degree);
    }

    //-------- OPTION 3
    @PostMapping("/neo4j/v3/relationship")
    public NPersonNode3 createNRelationship3(@RequestBody NRelationshipData3 nRelationshipData) {
        return relationshipService.createNPersonRelationship3(nRelationshipData);
    }

    @GetMapping("/neo4j/v3/person/{id}")
    public NPersonNode3 getNRelationshipsByPersonId3(@PathVariable("id") Long id,
            @Nullable @RequestParam("degree") Integer degree) {
        return relationshipService.getNPersonRelationships3ByPersonId(id, degree);
    }

    //-------- OPTION 4
    @PostMapping("/neo4j/v4.1/relationship")
    public NPersonNode4 createNRelationship4(@RequestBody NRelationshipData3 nRelationshipData) {
        return relationshipService.createNPersonRelationship4(nRelationshipData);
    }

    @GetMapping("/neo4j/v4.1/person/{id}")
    public NPersonNode4 getNRelationshipsByPersonId4(@PathVariable("id") Long id,
            @Nullable @RequestParam("degree") Integer degree) {
        return relationshipService.getNPersonRelationships4ByPersonId(id, degree);
    }

    @PostMapping("/neo4j/v4.2/relationship")
    public NPersonNode4List createNRelationship4List(
            @RequestBody NRelationshipData3 nRelationshipData) {
        return relationshipService.createNPersonRelationship4List(nRelationshipData);
    }

    @GetMapping("/neo4j/v4.2/person/{id}")
    public NPersonNode4List getNRelationshipsByPersonId4List(@PathVariable("id") Long id,
            @Nullable @RequestParam("degree") Integer degree) {
        return relationshipService.getNPersonRelationships4ListByPersonId(id, degree);
    }

    //-------- OPTION 5
    @PostMapping("/neo4j/v5.1/relationship")
    public NPersonNode5 createNRelationship5(@RequestBody NRelationshipData3 nRelationshipData) {
        return relationshipService.createNPersonRelationship5(nRelationshipData);
    }

    // NOTE: Incomplete as conversions need to be done manually as Neo4j OGM is unable to convert List<StringValue> to List<String> itself
    // "Could not write JSON: class org.neo4j.driver.internal.value.StringValue cannot be cast to class java.lang.String..."
    @PostMapping("/neo4j/v5.2/relationship")
    public NPersonNode5List createNRelationship5List(
            @RequestBody NRelationshipData3 nRelationshipData) {
        return relationshipService.createNPersonRelationship5List(nRelationshipData);
    }
}
