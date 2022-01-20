package com.qingru.graph.service;

import com.qingru.graph.arangoRepository.PersonRelationshipRepository;
import com.qingru.graph.domain.arango.PersonNode;
import com.qingru.graph.domain.arango.PersonRelationshipEdge;
import com.qingru.graph.domain.neo4j.NPersonNode;
import com.qingru.graph.domain.neo4j.NPersonRelationshipEdge;
import com.qingru.graph.domain.neo4j.RelationshipData;
import com.qingru.graph.neo4jRepository.NPersonNodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class RelationshipService {

    @Autowired
    private PersonRelationshipRepository personRelationshipRepository;

    @Autowired
    private NPersonNodeRepository nPersonNodeRepository;

    public PersonRelationshipEdge createPersonRelationship(PersonRelationshipEdge edge) {
        log.info(edge.toString());
        return personRelationshipRepository.save(edge);
    }

    public List<PersonRelationshipEdge> getPersonRelationshipsByPerson(PersonNode personNode,
            Integer degree) {
        if (degree != null) {
            return personRelationshipRepository
                    .findPersonRelationshipsWithDegree(personNode.getId(), degree);
        } else {
            return personRelationshipRepository.findAllPersonRelationships(personNode.getId());

        }
    }

    public NPersonNode createNPersonRelationship(RelationshipData relationshipData) {
        NPersonNode fromPerson =
                nPersonNodeRepository.findNPersonNodesById(relationshipData.getFromPersonId())
                        .orElse(null);
        NPersonNode toPerson =
                nPersonNodeRepository.findNPersonNodesById(relationshipData.getToPersonId())
                        .orElse(null);
        List<NPersonRelationshipEdge> existingRelations = fromPerson.getRelations();
        NPersonRelationshipEdge relation =
                new NPersonRelationshipEdge(toPerson, relationshipData.getRelationshipType(),
                        relationshipData.getRelationship());
        existingRelations.add(relation);
        fromPerson.setRelations(existingRelations);
        return nPersonNodeRepository.save(fromPerson);
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
