package com.qingru.graph.service;

import com.qingru.graph.arangoRepository.PersonNodeRepository;
import com.qingru.graph.domain.arango.PersonNode;
import com.qingru.graph.domain.neo4j.common.NPersonNode;
import com.qingru.graph.domain.neo4j.optionOne.NPersonNode1;
import com.qingru.graph.domain.neo4j.optionTwo.NPersonNode2;
import com.qingru.graph.neo4jRepository.NPersonRelationshipRepository;
import com.qingru.graph.neo4jRepository.optionOne.NPersonRelationship1Repository;
import com.qingru.graph.neo4jRepository.optionTwo.NPersonRelationship2Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class PersonService {

    private RelationshipService relationshipService;
    private PersonNodeRepository personNodeRepository;
    private NPersonRelationshipRepository nPersonRelationshipRepository;
    private NPersonRelationship1Repository nPersonRelationship1Repository;
    private NPersonRelationship2Repository nPersonRelationship2Repository;

    // [TEMP] This creates nodes in both Neo4j and Arango
    public PersonNode createPerson(PersonNode personNode) {
        log.info(personNode.toString());
        NPersonNode nPersonNode =
                NPersonNode.builder().username(personNode.getUsername()).age(personNode.getAge())
                        .description(personNode.getDescription()).imageUrl(personNode.getImageUrl())
                        .relations(new ArrayList<>()).build();
        NPersonNode createdNPersonNode = nPersonRelationshipRepository.save(nPersonNode);
        log.info(createdNPersonNode.toString());
        return personNodeRepository.save(personNode);
    }

    public NPersonNode createNPerson(NPersonNode nPersonNode) {
        return nPersonRelationshipRepository.save(nPersonNode);
    }

    public NPersonNode updateNPerson(NPersonNode nPersonNode) {
        return createNPerson(nPersonNode);
    }

    public PersonNode getPersonByUsername(String username) {
        return personNodeRepository.findByUsername(username).orElse(null);
    }

    public PersonNode getNPerson1ById(String id) {
        return personNodeRepository.findById(id).orElse(null);
    }

    public NPersonNode getNPersonById(Long id) {
        return nPersonRelationshipRepository.findById(id).orElse(null);
    }

    public void deletePersonByPersonId(String id) {
        relationshipService.deletePersonRelationshipByPersonId(id);
        personNodeRepository.deleteById(id);
    }

    //-------- OPTION 1
    public NPersonNode1 getNPerson1ById(Long id) {
        return nPersonRelationship1Repository.findNPersonNode1ById(id).orElseGet(null);
    }

    public NPersonNode1 createNPerson1(NPersonNode1 nPersonNode1) {
        return nPersonRelationship1Repository.save(nPersonNode1);
    }

    //-------- OPTION 2
    public NPersonNode2 getNPerson2ById(Long id) {
        return nPersonRelationship2Repository.findNPersonNode2ById(id);
    }

    public NPersonNode2 createNPerson2(NPersonNode2 nPersonNode2) {
        return nPersonRelationship2Repository.save(nPersonNode2);
    }

}

