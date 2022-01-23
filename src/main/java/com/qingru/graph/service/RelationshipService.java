package com.qingru.graph.service;

import com.qingru.graph.arangoRepository.PersonNodeRepository;
import com.qingru.graph.arangoRepository.PersonRelationshipRepository;
import com.qingru.graph.domain.RelationshipData;
import com.qingru.graph.domain.arango.PersonNode;
import com.qingru.graph.domain.arango.PersonRelationshipEdge;
import com.qingru.graph.domain.neo4j.NPersonNode;
import com.qingru.graph.domain.neo4j.NPersonRelationshipEdge;
import com.qingru.graph.neo4jRepository.NPersonRelationshipRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class RelationshipService {

    private PersonRelationshipRepository personRelationshipRepository;
    private NPersonRelationshipRepository nPersonRelationshipRepository;
    private PersonNodeRepository personNodeRepository;

    public List<PersonRelationshipEdge> getPersonRelationshipsByPerson(PersonNode personNode,
            Integer degree) {
        if (degree != null) {
            return personRelationshipRepository
                    .findPersonRelationshipsWithDegree(personNode.getId(), degree);
        } else {
            return personRelationshipRepository.findAllPersonRelationships(personNode.getId());

        }
    }

    public PersonRelationshipEdge createPersonRelationship(RelationshipData relationshipData) {
        PersonNode fromPerson =
                personNodeRepository.findById(String.valueOf(relationshipData.getFromPersonId()))
                        .orElseThrow(() -> new IllegalArgumentException(
                                String.format("No person of id: %l",
                                        relationshipData.getFromPersonId())));
        PersonNode toPerson =
                personNodeRepository.findById(String.valueOf(relationshipData.getToPersonId()))
                        .orElseThrow(() -> new IllegalArgumentException(
                                String.format("No person of id: %l",
                                        relationshipData.getToPersonId())));
        PersonRelationshipEdge relation =
                PersonRelationshipEdge.builder().fromPersonNode(fromPerson).toPersonNode(toPerson)
                        .type(relationshipData.getRelationshipType()).build();
        return personRelationshipRepository.save(relation);
    }

    public List<Object> getNPersonRelationshipsByPersonId(String personId, Integer degree) {
        if (degree != null) {
            return nPersonRelationshipRepository
                    .findPersonRelationshipsWithDegree(personId, degree);
        } else {
            return nPersonRelationshipRepository
                    .findAllPersonRelationships(Integer.parseInt(personId));
        }
    }

    public NPersonNode createNPersonRelationship(RelationshipData relationshipData) {
        NPersonNode fromPerson = nPersonRelationshipRepository
                .findNPersonNodesById(relationshipData.getFromPersonId()).orElse(null);
        NPersonNode toPerson =
                nPersonRelationshipRepository.findNPersonNodesById(relationshipData.getToPersonId())
                        .orElse(null);
        List<NPersonRelationshipEdge> existingRelations = fromPerson.getRelations();
        NPersonRelationshipEdge relation =
                new NPersonRelationshipEdge(toPerson, relationshipData.getRelationshipType(),
                        relationshipData.getRelationship());
        existingRelations.add(relation);
        fromPerson.setRelations(existingRelations);
        return nPersonRelationshipRepository.save(fromPerson);
    }

    public void updateNPersonRelationship(String relationshipId,
            RelationshipData relationshipData) {
        nPersonRelationshipRepository.updatePersonRelationship(Integer.parseInt(relationshipId),
                relationshipData.getRelationshipType());
    }

    public void deletePersonRelationshipByEdgeId(String id) {
        boolean exists = personRelationshipRepository.existsById(id);
        if (exists) {
            personRelationshipRepository.deleteById(id);
        } else {
            log.warn("Relationship id: {} not found to be deleted", id);
        }
    }

    public void deletePersonRelationshipByPersonId(String id) {
        boolean exists = personRelationshipRepository.findAllPersonRelationships(id).size() > 0;
        if (exists) {
            personRelationshipRepository.deletePersonRelationshipsByPersonId(id);
        }
    }
}
