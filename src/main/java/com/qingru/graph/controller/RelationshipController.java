package com.qingru.graph.controller;

import com.qingru.graph.domain.arango.PersonNode;
import com.qingru.graph.domain.arango.PersonRelationshipEdge;
import com.qingru.graph.domain.neo4j.NPersonNode;
import com.qingru.graph.domain.neo4j.RelationshipData;
import com.qingru.graph.service.PersonService;
import com.qingru.graph.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/arango")
    public ResponseEntity<PersonRelationshipEdge> createRelationship(
            @RequestBody PersonRelationshipEdge relationshipEdge) {
        return ResponseEntity.ok(relationshipService.createPersonRelationship(relationshipEdge));
    }

    @GetMapping("/arango/{id}")
    public ResponseEntity<List<PersonNode>> getRelationshipsByPersonId(
            @PathVariable("id") String id, @Nullable @RequestParam("degree") Integer degree) {
        PersonNode personNode = personService.getPersonById(id);
        List<PersonNode> edges =
                relationshipService.getPersonRelationshipsByPerson(personNode, degree);
        return ResponseEntity.ok(edges);
    }

    @PostMapping("/neo4j")
    public ResponseEntity<NPersonNode> createNRelationship(
            @RequestBody RelationshipData relationshipData) {
        return ResponseEntity.ok(relationshipService.createNPersonRelationship(relationshipData));
    }

}
