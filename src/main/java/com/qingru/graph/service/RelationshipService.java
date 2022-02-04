package com.qingru.graph.service;

import com.qingru.graph.arangoRepository.PersonNodeRepository;
import com.qingru.graph.arangoRepository.PersonRelationshipRepository;
import com.qingru.graph.domain.RelationshipData;
import com.qingru.graph.domain.arango.PersonNode;
import com.qingru.graph.domain.arango.PersonRelationshipEdge;
import com.qingru.graph.domain.neo4j.NPersonRelationshipEdge;
import com.qingru.graph.domain.neo4j.common.NPersonNode;
import com.qingru.graph.domain.neo4j.common.NRelationshipData;
import com.qingru.graph.domain.neo4j.optionOne.NFlattenedRelationshipEdge;
import com.qingru.graph.domain.neo4j.optionOne.NPersonNode1;
import com.qingru.graph.domain.neo4j.optionOne.NPersonRelationshipResult;
import com.qingru.graph.domain.neo4j.optionOne.NSourceNode;
import com.qingru.graph.neo4jRepository.NPersonRelationship1Repository;
import com.qingru.graph.neo4jRepository.NPersonRelationshipRepository;
import com.qingru.graph.neo4jRepository.NSource1Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.internal.value.MapValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class RelationshipService {

    private PersonRelationshipRepository personRelationshipRepository;
    private NPersonRelationshipRepository nPersonRelationshipRepository;
    private PersonNodeRepository personNodeRepository;

    //-------- OPTION 1
    private NPersonRelationship1Repository nPersonRelationship1Repository;
    private NSource1Repository nSource1Repository;

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
                        .type(relationshipData.getRelationshipType())
                        .relationshipMetadata(relationshipData.getRelationshipMetadata()).build();
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
        NPersonNode fromPerson = getNPersonNodeById(relationshipData.getFromPersonId());
        NPersonNode toPerson = getNPersonNodeById(relationshipData.getToPersonId());
        List<NPersonRelationshipEdge> existingRelations = fromPerson.getRelations();
        NPersonRelationshipEdge relation =
                new NPersonRelationshipEdge(toPerson, relationshipData.getRelationshipType());
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

    private NPersonNode getNPersonNodeById(Long id) {
        return nPersonRelationshipRepository.findNPersonNodesById(id).orElseThrow(
                () -> new IllegalArgumentException(String.format("No person of id: %l", id)));
    }

    //-------- OPTION 1
    public NPersonRelationshipResult createPersonRelationship1(
            NRelationshipData nRelationshipData) {
        //         Check if person A and B exist
        NPersonNode1 fromPerson = getNPersonNode1ById(nRelationshipData.getFromPersonId());
        NPersonNode1 toPerson = getNPersonNode1ById(nRelationshipData.getToPersonId());

        // Create Source Node (if required)
        NSourceNode nSourceNode =
                nSource1Repository.save(nRelationshipData.getRelationship().getSource());

        // Create relationship between person A and Source Node (Assumption here is multiple relationship can form between them, hence no need to check)
        List<NFlattenedRelationshipEdge> fromPersonExistingRelations =
                fromPerson.getRelationships();
        NFlattenedRelationshipEdge fromPersonNewRelation =
                new NFlattenedRelationshipEdge(nSourceNode,
                        nRelationshipData.getRelationship().getRelationshipType(),
                        nRelationshipData.getRelationship().getCloseness());
        fromPersonExistingRelations.add(fromPersonNewRelation);
        fromPerson.setRelationships(fromPersonExistingRelations);
        nPersonRelationship1Repository.save(fromPerson);

        // Create relationship between person B and Source Node (Assumption here is multiple relationship can form between them, hence no need to check)
        List<NFlattenedRelationshipEdge> toPersonExistingRelations = toPerson.getRelationships();
        NFlattenedRelationshipEdge toPersonNewRelation = new NFlattenedRelationshipEdge(nSourceNode,
                nRelationshipData.getRelationship().getRelationshipType(),
                nRelationshipData.getRelationship().getCloseness());
        toPersonExistingRelations.add(toPersonNewRelation);
        toPerson.setRelationships(toPersonExistingRelations);
        nPersonRelationship1Repository.save(toPerson);

        //        Return the created relationship
        NPersonNode1 n = getPersonAndRelationshipBySourceIdAndRelationshipId(nSourceNode.getId(),
                toPersonNewRelation.getId());
        return null;
    }

    private NPersonNode1 getNPersonNode1ById(Long id) {
        return nPersonRelationship1Repository.findNPersonNode1ById(id).orElseThrow(
                () -> new IllegalArgumentException(String.format("No person of id: %l", id)));
    }

    // May deprecate this method as getPersonsAndRelationshipWithDegreeByPersonId works for degree = 2/2 too and no need mapping
    public List<NPersonRelationshipResult> getPersonsAndDirectRelationshipByPersonId(Long id) {
        Optional<List<Object>> neo4jResult =
                nPersonRelationship1Repository.findPersonAndRelationshipByPersonId(id);
        if (neo4jResult.get() == null) {
            return null;
        }
        List<NPersonRelationshipResult> personRelationshipResults = new ArrayList<>();
        for (Object personRelationship : neo4jResult.get()) {
            MapValue mapValue = (MapValue) personRelationship;
            Map<String, Object> map = mapValue.asMap();
            personRelationshipResults.add(new NPersonRelationshipResult(map));
        }
        return personRelationshipResults;
    }

    // This returns the direct person and the degree/2 relations from the direct persons
    public List<NPersonNode1> getPersonsAndRelationshipWithDegreeByPersonId(Long sourceId,
            int degree) {
        List<NPersonNode1> neo4jResult = nPersonRelationship1Repository
                .findPersonAndRelationshipWithDegreeByPersonId(sourceId, degree).orElseGet(null);

        return neo4jResult;
    }

    public NPersonNode1 getPersonAndRelationshipBySourceIdAndRelationshipId(Long sourceId,
            Long relationshipId) {
        return nPersonRelationship1Repository
                .findPersonAndRelationshipBySourceIdAndRelationshipId(sourceId, relationshipId)
                .orElseGet(null);
    }
}
