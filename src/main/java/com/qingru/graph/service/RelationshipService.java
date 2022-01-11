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

    public List<PersonNode> getPersonRelationshipsByPerson(PersonNode personNode, Integer degree) {
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
        NPersonRelationshipEdge relation =
                new NPersonRelationshipEdge(toPerson, relationshipData.getRelationshipType(),
                        relationshipData.getRelationshipHistory());
        fromPerson.setRelations(List.of(relation));
        return nPersonNodeRepository.save(fromPerson);
    }
}
