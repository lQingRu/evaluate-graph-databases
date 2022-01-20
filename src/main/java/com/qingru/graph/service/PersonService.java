package com.qingru.graph.service;

import com.qingru.graph.arangoRepository.PersonNodeRepository;
import com.qingru.graph.domain.arango.PersonNode;
import com.qingru.graph.domain.neo4j.NPersonNode;
import com.qingru.graph.neo4jRepository.NPersonNodeRepository;
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
    private NPersonNodeRepository nPersonNodeRepository;

    // [TEMP] This creates nodes in both Neo4j and Arango
    public PersonNode createPerson(PersonNode personNode) {
        log.info(personNode.toString());
        NPersonNode nPersonNode =
                NPersonNode.builder().username(personNode.getUsername()).age(personNode.getAge())
                        .description(personNode.getDescription()).imageUrl(personNode.getImageUrl())
                        .relations(new ArrayList<>()).build();
        NPersonNode createdNPersonNode = nPersonNodeRepository.save(nPersonNode);
        log.info(createdNPersonNode.toString());
        return personNodeRepository.save(personNode);
    }

    public NPersonNode createNPerson(NPersonNode nPersonNode) {
        return nPersonNodeRepository.save(nPersonNode);
    }

    public PersonNode getPersonByUsername(String username) {
        return personNodeRepository.findByUsername(username).orElse(null);
    }

    public PersonNode getPersonById(String id) {
        return personNodeRepository.findById(id).orElse(null);
    }

    public NPersonNode getNPersonById(Long id) {
        return nPersonNodeRepository.findById(id).orElse(null);
    }

    public void deletePersonByPersonId(String id) {
        relationshipService.deletePersonRelationshipByPersonId(id);
        personNodeRepository.deleteById(id);
    }
}

