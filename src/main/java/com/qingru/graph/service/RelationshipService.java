package com.qingru.graph.service;

import com.qingru.graph.arangoRepository.PersonNodeRepository;
import com.qingru.graph.arangoRepository.PersonRelationshipRepository;
import com.qingru.graph.domain.arango.PersonNode;
import com.qingru.graph.domain.arango.PersonRelationshipEdge;
import com.qingru.graph.domain.neo4j.common.NRelationshipData;
import com.qingru.graph.domain.neo4j.common.Source;
import com.qingru.graph.domain.neo4j.optionFive.NPersonNode5;
import com.qingru.graph.domain.neo4j.optionFive.NPersonNode5List;
import com.qingru.graph.domain.neo4j.optionFive.NRelationshipEdge5;
import com.qingru.graph.domain.neo4j.optionFive.NRelationshipEdge5List;
import com.qingru.graph.domain.neo4j.optionFour.NPersonNode4;
import com.qingru.graph.domain.neo4j.optionFour.NPersonNode4List;
import com.qingru.graph.domain.neo4j.optionFour.NRelationshipEdge4;
import com.qingru.graph.domain.neo4j.optionFour.NRelationshipEdge4List;
import com.qingru.graph.domain.neo4j.optionOne.*;
import com.qingru.graph.domain.neo4j.optionThree.NPersonNode3;
import com.qingru.graph.domain.neo4j.optionThree.NRelationshipData3;
import com.qingru.graph.domain.neo4j.optionTwo.NPersonNode2;
import com.qingru.graph.neo4jRepository.optionFive.NPersonRelationship5ListRepository;
import com.qingru.graph.neo4jRepository.optionFive.NPersonRelationship5Repository;
import com.qingru.graph.neo4jRepository.optionFour.NPersonRelationship4ListRepository;
import com.qingru.graph.neo4jRepository.optionFour.NPersonRelationship4Repository;
import com.qingru.graph.neo4jRepository.optionOne.NPersonRelationship1Repository;
import com.qingru.graph.neo4jRepository.optionOne.NSource1Repository;
import com.qingru.graph.neo4jRepository.optionThree.NPersonRelationship3Repository;
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
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class RelationshipService {

    private PersonRelationshipRepository personRelationshipRepository;
    private PersonNodeRepository personNodeRepository;

    //-------- OPTION 1
    private NPersonRelationship1Repository nPersonRelationship1Repository;
    private NSource1Repository nSource1Repository;

    //-------- OPTION 2
    private NPersonRelationship2Repository nPersonRelationship2Repository;

    //-------- OPTION 3
    private NPersonRelationship3Repository nPersonRelationship3Repository;

    //-------- OPTION 4
    private NPersonRelationship4Repository nPersonRelationship4Repository;
    private NPersonRelationship4ListRepository nPersonRelationship4ListRepository;

    //-------- OPTION 5
    private NPersonRelationship5Repository nPersonRelationship5Repository;
    private NPersonRelationship5ListRepository nPersonRelationship5ListRepository;

    // Arango
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

    // Neo4j
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
    public NPersonNode2 getNPersonRelationships2ByPersonId(Long personId, Integer degree) {
        if (degree == null) {
            degree = 1;
        }
        List<NPersonNode2> allPersonNodesLinkedToPerson =
                nPersonRelationship2Repository.findPersonRelationshipsWithDegree(personId, degree);
        return !allPersonNodesLinkedToPerson.isEmpty() ?
                allPersonNodesLinkedToPerson.stream()
                        .filter(personNode -> personNode.getId().equals(personId)).findFirst()
                        .orElse(null) :
                null;
    }

    public NPersonNode2 createNPersonRelationship2(NRelationshipData relationshipData) {
        return nPersonRelationship2Repository
                .createOutgoingRelationship(relationshipData.getFromPersonId(),
                        relationshipData.getToPersonId(),
                        relationshipData.getRelationshipMetadata().getCloseness(),
                        relationshipData.getRelationshipMetadata().getSource().getType(),
                        relationshipData.getRelationshipMetadata().getSource().getDescription(),
                        relationshipData.getRelationshipMetadata().getSource().getStartDate(),
                        relationshipData.getRelationshipType());
    }

    //-------- OPTION 3
    public NPersonNode3 getNPersonRelationships3ByPersonId(Long personId, Integer degree) {
        if (degree == null) {
            degree = 1;
        }
        List<NPersonNode3> allPersonNodesLinkedToPerson =
                nPersonRelationship3Repository.findPersonRelationshipsWithDegree(personId, degree);
        return !allPersonNodesLinkedToPerson.isEmpty() ?
                allPersonNodesLinkedToPerson.stream()
                        .filter(personNode -> personNode.getId().equals(personId)).findFirst()
                        .orElse(null) :
                null;
    }

    public NPersonNode3 createNPersonRelationship3(NRelationshipData3 relationshipData) {
        List<Source> sources = relationshipData.getRelationshipMetadata().getSource();

        return nPersonRelationship3Repository
                .createOutgoingRelationship(relationshipData.getFromPersonId(),
                        relationshipData.getToPersonId(),
                        relationshipData.getRelationshipMetadata().getCloseness(), sources.stream()
                                .map(source -> source.getType() == null ? "" : source.getType())
                                .filter(element -> element != null).collect(Collectors.toList()),
                        sources.stream().map(source -> source.getDescription() == null ?
                                "" :
                                source.getDescription()).filter(element -> element != null)
                                .collect(Collectors.toList()), sources.stream()
                                .map(source -> source.getStartDate() == null ?
                                        "" :
                                        source.getStartDate().toString())
                                .filter(element -> element != null).collect(Collectors.toList()),
                        relationshipData.getRelationshipType());
    }

    //-------- OPTION 4
    public NPersonNode4 createNPersonRelationship4(NRelationshipData3 relationshipData) {
        NPersonNode4 fromPersonNode = nPersonRelationship4Repository
                .findNPersonNode4ById(relationshipData.getFromPersonId());
        NPersonNode4 toPersonNode = nPersonRelationship4Repository
                .findNPersonNode4ById(relationshipData.getToPersonId());
        if (fromPersonNode == null) {
            fromPersonNode =
                    nPersonRelationship4Repository.findById(relationshipData.getFromPersonId())
                            .orElseThrow(IllegalArgumentException::new);
        }
        if (toPersonNode == null) {
            toPersonNode = nPersonRelationship4Repository.findById(relationshipData.getToPersonId())
                    .orElseThrow(IllegalArgumentException::new);
        }
        Source source = relationshipData.getRelationshipMetadata().getSource().get(0);
        NRelationshipEdge4 edge = new NRelationshipEdge4(toPersonNode, "close", "Type", source);
        fromPersonNode.setOutgoingRelationships(List.of(edge));
        nPersonRelationship4Repository.save(fromPersonNode);
        return nPersonRelationship4Repository.findNPersonNode4ById(fromPersonNode.getId());
    }

    public NPersonNode4List createNPersonRelationship4List(NRelationshipData3 relationshipData) {
        NPersonNode4List fromPersonNode = nPersonRelationship4ListRepository
                .findNPersonNode4ListById(relationshipData.getFromPersonId());
        if (fromPersonNode == null) {
            fromPersonNode =
                    nPersonRelationship4ListRepository.findById(relationshipData.getFromPersonId())
                            .orElseThrow(IllegalArgumentException::new);
        }
        NPersonNode4List toPersonNode = nPersonRelationship4ListRepository
                .findNPersonNode4ListById(relationshipData.getToPersonId());
        if (toPersonNode == null) {
            toPersonNode =
                    nPersonRelationship4ListRepository.findById(relationshipData.getToPersonId())
                            .orElseThrow(IllegalArgumentException::new);
        }
        List<Source> sources = relationshipData.getRelationshipMetadata().getSource();
        NRelationshipEdge4List edge = new NRelationshipEdge4List(toPersonNode,
                relationshipData.getRelationshipMetadata().getCloseness(),
                relationshipData.getRelationshipMetadata().getType(), sources);
        fromPersonNode.setOutgoingRelationships(List.of(edge));
        nPersonRelationship4ListRepository.save(fromPersonNode);
        return nPersonRelationship4ListRepository.findNPersonNode4ListById(fromPersonNode.getId());
    }

    //-------- OPTION 5
    public NPersonNode5 createNPersonRelationship5(NRelationshipData3 relationshipData) {
        NPersonNode5 fromPersonNode = nPersonRelationship5Repository
                .findNPersonNode5ById(relationshipData.getFromPersonId());
        NPersonNode5 toPersonNode = nPersonRelationship5Repository
                .findNPersonNode5ById(relationshipData.getToPersonId());
        if (fromPersonNode == null) {
            fromPersonNode =
                    nPersonRelationship5Repository.findById(relationshipData.getFromPersonId())
                            .orElseThrow(IllegalArgumentException::new);
        }
        if (toPersonNode == null) {
            toPersonNode = nPersonRelationship5Repository.findById(relationshipData.getToPersonId())
                    .orElseThrow(IllegalArgumentException::new);
        }
        Source source = relationshipData.getRelationshipMetadata().getSource().get(0);
        NRelationshipEdge5 edge = new NRelationshipEdge5(toPersonNode, "close", "Type", source);
        fromPersonNode.setOutgoingRelationships(List.of(edge));
        nPersonRelationship5Repository.save(fromPersonNode);
        return nPersonRelationship5Repository.findNPersonNode5ById(fromPersonNode.getId());
    }

    public NPersonNode5List createNPersonRelationship5List(NRelationshipData3 relationshipData) {
        NPersonNode5List fromPersonNode = nPersonRelationship5ListRepository
                .findNPersonNode5ListById(relationshipData.getFromPersonId());
        if (fromPersonNode == null) {
            fromPersonNode =
                    nPersonRelationship5ListRepository.findById(relationshipData.getFromPersonId())
                            .orElseThrow(IllegalArgumentException::new);
        }
        NPersonNode5List toPersonNode = nPersonRelationship5ListRepository
                .findNPersonNode5ListById(relationshipData.getToPersonId());
        if (toPersonNode == null) {
            toPersonNode =
                    nPersonRelationship5ListRepository.findById(relationshipData.getToPersonId())
                            .orElseThrow(IllegalArgumentException::new);
        }
        List<Source> sources = relationshipData.getRelationshipMetadata().getSource();
        NRelationshipEdge5List edge = new NRelationshipEdge5List(toPersonNode,
                relationshipData.getRelationshipMetadata().getCloseness(),
                relationshipData.getRelationshipMetadata().getType(), sources);
        fromPersonNode.setOutgoingRelationships(List.of(edge));
        nPersonRelationship5ListRepository.save(fromPersonNode);
        return nPersonRelationship5ListRepository.findNPersonNode5ListById(fromPersonNode.getId());
    }
}
