package com.qingru.graph.controller;

import com.qingru.graph.domain.RelationshipData;
import com.qingru.graph.domain.arango.PersonNode;
import com.qingru.graph.domain.arango.PersonRelationshipEdge;
import com.qingru.graph.domain.neo4j.common.NPersonNode;
import com.qingru.graph.domain.neo4j.common.NRelationshipData;
import com.qingru.graph.domain.neo4j.optionOne.NPersonRelationshipResult;
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
        PersonNode personNode = personService.getNPerson1ById(id);
        List<PersonRelationshipEdge> edges =
                relationshipService.getPersonRelationshipsByPerson(personNode, degree);
        return edges;
    }

    @PostMapping("/arango")
    public PersonRelationshipEdge createRelationship(
            @RequestBody RelationshipData relationshipData) {
        return relationshipService.createPersonRelationship(relationshipData);
    }

    @DeleteMapping("/arango/relationship/{id}")
    public void removeRelationshipByEdgeId(@PathVariable("id") String id) {
        relationshipService.deletePersonRelationshipByEdgeId(id);
    }

    @GetMapping("/neo4j/person/{id}")
    public List<Object> getNRelationshipsByPersonId(@PathVariable("id") String id,
            @Nullable @RequestParam("degree") Integer degree) {
        List<Object> edges = relationshipService.getNPersonRelationshipsByPersonId(id, degree);
        return edges;
    }

    @PostMapping("/neo4j")
    public NPersonNode createNRelationship(@RequestBody RelationshipData relationshipData) {
        return relationshipService.createNPersonRelationship(relationshipData);
    }

    @PutMapping("/neo4j/relationship/{id}")
    public void updateNRelationship(@PathVariable("id") String id,
            @RequestBody RelationshipData relationshipData) {
        relationshipService.updateNPersonRelationship(id, relationshipData);
    }

    //-------- OPTION 1
    @PostMapping("/neo4j/v1/relationship")
    public NPersonRelationshipResult createNRelationship1(
            @RequestBody NRelationshipData nRelationshipData) {
        return relationshipService.createPersonRelationship1(nRelationshipData);
    }

    // This API will also return relationship because we assume that whenever we want to retrieve
    // person object, we want to retrieve together with the relationship
    @GetMapping("/neo4j/v1/person/{id}")
    public NPersonRelationshipResult getNPerson1ById(@PathVariable("id") Long id) {
        return relationshipService.getPersonsAndRelationshipByPersonId(id);
    }
}
