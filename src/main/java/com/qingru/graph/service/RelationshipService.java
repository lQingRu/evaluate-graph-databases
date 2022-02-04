package com.qingru.graph.service;

import com.qingru.graph.arangoRepository.PersonNodeRepository;
import com.qingru.graph.arangoRepository.PersonRelationshipRepository;
import com.qingru.graph.domain.arango.PersonNode;
import com.qingru.graph.domain.arango.PersonRelationshipEdge;
import com.qingru.graph.domain.neo4j.NPersonRelationshipEdge;
import com.qingru.graph.domain.neo4j.common.NPersonNode;
import com.qingru.graph.domain.neo4j.common.NRelationshipData;
import com.qingru.graph.domain.neo4j.optionOne.*;
import com.qingru.graph.domain.neo4j.optionTwo.NFlattenedRelationshipEdge2;
import com.qingru.graph.domain.neo4j.optionTwo.NPersonNode2;
import com.qingru.graph.neo4jRepository.NPersonRelationshipRepository;
import com.qingru.graph.neo4jRepository.optionOne.NPersonRelationship1Repository;
import com.qingru.graph.neo4jRepository.optionOne.NSource1Repository;
import com.qingru.graph.neo4jRepository.optionTwo.NPersonRelationship2Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.internal.value.MapValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    //-------- OPTION 2
    private NPersonRelationship2Repository nPersonRelationship2Repository;

    public List<PersonRelationshipEdge> getPersonRelationshipsByPerson(PersonNode personNode,
            Integer degree) {
        if (degree != null) {
            return personRelationshipRepository
                    .findPersonRelationshipsWithDegree(personNode.getId(), degree);
        } else {
            return personRelationshipRepository.findAllPersonRelationships(personNode.getId());

        }
    }

    public PersonRelationshipEdge createPersonRelationship(NRelationshipData NRelationshipData) {
        PersonNode fromPerson =
                personNodeRepository.findById(String.valueOf(NRelationshipData.getFromPersonId()))
                        .orElseThrow(() -> new IllegalArgumentException(
                                String.format("No person of id: %l",
                                        NRelationshipData.getFromPersonId())));
        PersonNode toPerson =
                personNodeRepository.findById(String.valueOf(NRelationshipData.getToPersonId()))
                        .orElseThrow(() -> new IllegalArgumentException(
                                String.format("No person of id: %l",
                                        NRelationshipData.getToPersonId())));
        PersonRelationshipEdge relation =
                PersonRelationshipEdge.builder().fromPersonNode(fromPerson).toPersonNode(toPerson)
                        .type(NRelationshipData.getRelationshipType())
                        .relationshipMetadata(NRelationshipData.getRelationshipMetadata()).build();
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

    public NPersonNode createNPersonRelationship(NRelationshipData NRelationshipData) {
        NPersonNode fromPerson = getNPersonNodeById(NRelationshipData.getFromPersonId());
        NPersonNode toPerson = getNPersonNodeById(NRelationshipData.getToPersonId());
        List<NPersonRelationshipEdge> existingRelations = fromPerson.getRelations();
        NPersonRelationshipEdge relation =
                new NPersonRelationshipEdge(toPerson, NRelationshipData.getRelationshipType());
        existingRelations.add(relation);
        fromPerson.setRelations(existingRelations);
        return nPersonRelationshipRepository.save(fromPerson);
    }

    public void updateNPersonRelationship(String relationshipId,
            NRelationshipData NRelationshipData) {
        nPersonRelationshipRepository.updatePersonRelationship(Integer.parseInt(relationshipId),
                NRelationshipData.getRelationshipType());
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
    public NPersonNode1 createPersonRelationship1(NRelationshipData1 nRelationshipData1) {
        // Check if person A and B exist
        NPersonNode1 fromPerson = getNPersonNode1ById(nRelationshipData1.getFromPersonId());
        NPersonNode1 toPerson = getNPersonNode1ById(nRelationshipData1.getToPersonId());

        // Create Source Node (if required)
        NSourceNode1 nSourceNode1 =
                nSource1Repository.save(nRelationshipData1.getRelationship().getSource());

        // Create relationship between person A and Source Node (Assumption here is multiple relationship can form between them, hence no need to check)
        List<NFlattenedRelationshipEdge1> fromPersonExistingRelations =
                fromPerson.getRelationships();
        NFlattenedRelationshipEdge1 fromPersonNewRelation =
                new NFlattenedRelationshipEdge1(nSourceNode1,
                        nRelationshipData1.getRelationship().getRelationshipType(),
                        nRelationshipData1.getRelationship().getCloseness());
        fromPersonExistingRelations.add(fromPersonNewRelation);
        fromPerson.setRelationships(fromPersonExistingRelations);
        nPersonRelationship1Repository.save(fromPerson);

        // Create relationship between person B and Source Node (Assumption here is multiple relationship can form between them, hence no need to check)
        List<NFlattenedRelationshipEdge1> toPersonExistingRelations = toPerson.getRelationships();
        NFlattenedRelationshipEdge1 toPersonNewRelation =
                new NFlattenedRelationshipEdge1(nSourceNode1,
                        nRelationshipData1.getRelationship().getRelationshipType(),
                        nRelationshipData1.getRelationship().getCloseness());
        toPersonExistingRelations.add(toPersonNewRelation);
        toPerson.setRelationships(toPersonExistingRelations);
        nPersonRelationship1Repository.save(toPerson);

        // TODO: Return the created relationship
        NPersonNode1 n = getPersonAndRelationshipBySourceIdAndRelationshipId(nSourceNode1.getId(),
                toPersonNewRelation.getId());
        return n;
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

    //-------- OPTION 2
    public Object getNPersonRelationships2ByPersonId(String personId, Integer degree) {
        if (degree != null) {
            Object o = nPersonRelationship2Repository
                    .findPersonRelationshipsWithDegree(personId, degree);
            return o;
        } else {
            Object o =
                    nPersonRelationship2Repository.findPersonRelationshipsWithDegree(personId, 1);
            return o;
        }
    }

    public NPersonNode2 createNPersonRelationship2(NRelationshipData relationshipData) {
        NPersonNode2 fromPerson = nPersonRelationship2Repository
                .findNPersonNode2ById2(relationshipData.getFromPersonId()).orElseGet(null);
        NPersonNode2 toPerson = nPersonRelationship2Repository
                .findNPersonNode2ById2(relationshipData.getToPersonId()).orElseGet(null);
        if (fromPerson == null || toPerson == null) {
            throw new IllegalArgumentException("Person(s) does not exist");
        }

        List<NFlattenedRelationshipEdge2> existingOutgoingRelationships =
                fromPerson.getOutgoingRelationships();
        NFlattenedRelationshipEdge2 newOutgoingRelationship = NFlattenedRelationshipEdge2.builder()
                .closeness(relationshipData.getRelationshipMetadata().getCloseness())
                .relationshipType(relationshipData.getRelationshipType())
                .sourceType(relationshipData.getRelationshipMetadata().getSource().getType())
                .sourceStartDate(null).sourceDescription(
                        relationshipData.getRelationshipMetadata().getSource().getDescription())
                .targetPersonNode(toPerson).build();
        existingOutgoingRelationships.add(newOutgoingRelationship);
        fromPerson.setOutgoingRelationships(existingOutgoingRelationships);
        return nPersonRelationship2Repository.save(fromPerson);
    }
}
