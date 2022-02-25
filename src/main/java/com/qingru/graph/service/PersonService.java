package com.qingru.graph.service;

import com.qingru.graph.arangoRepository.PersonNodeRepository;
import com.qingru.graph.domain.arango.PersonNode;
import com.qingru.graph.domain.neo4j.optionFive.NPersonNode5;
import com.qingru.graph.domain.neo4j.optionFive.NPersonNode5List;
import com.qingru.graph.domain.neo4j.optionFour.NPersonNode4;
import com.qingru.graph.domain.neo4j.optionFour.NPersonNode4List;
import com.qingru.graph.domain.neo4j.optionOne.NPersonNode1;
import com.qingru.graph.domain.neo4j.optionThree.NPersonNode3;
import com.qingru.graph.domain.neo4j.optionTwo.NPersonNode2;
import com.qingru.graph.neo4jRepository.optionFive.NPersonRelationship5ListRepository;
import com.qingru.graph.neo4jRepository.optionFive.NPersonRelationship5Repository;
import com.qingru.graph.neo4jRepository.optionFour.NPersonRelationship4ListRepository;
import com.qingru.graph.neo4jRepository.optionFour.NPersonRelationship4Repository;
import com.qingru.graph.neo4jRepository.optionOne.NPersonRelationship1Repository;
import com.qingru.graph.neo4jRepository.optionThree.NPersonRelationship3Repository;
import com.qingru.graph.neo4jRepository.optionTwo.NPersonRelationship2Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class PersonService {

    private RelationshipService relationshipService;
    private PersonNodeRepository personNodeRepository;
    private NPersonRelationship1Repository nPersonRelationship1Repository;
    private NPersonRelationship2Repository nPersonRelationship2Repository;
    private NPersonRelationship3Repository nPersonRelationship3Repository;
    private NPersonRelationship4Repository nPersonRelationship4Repository;
    private NPersonRelationship4ListRepository nPersonRelationship4ListRepository;
    private NPersonRelationship5Repository nPersonRelationship5Repository;
    private NPersonRelationship5ListRepository nPersonRelationship5ListRepository;

    // Neo4j
    public PersonNode createPerson(PersonNode personNode) {
        return personNodeRepository.save(personNode);
    }

    public PersonNode getPersonById(String id) {
        return personNodeRepository.findById(id).orElse(null);
    }

    public void deletePersonByPersonId(String id) {
        relationshipService.deletePersonRelationshipByPersonId(id);
        personNodeRepository.deleteById(id);
    }

    // Arango
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

    //-------- OPTION 3
    public NPersonNode3 getNPerson3ById(Long id) {
        return nPersonRelationship3Repository.findNPersonNode3ById(id);
    }

    public NPersonNode3 createNPerson3(NPersonNode3 nPersonNode3) {
        return nPersonRelationship3Repository.save(nPersonNode3);
    }

    //-------- OPTION 4
    public NPersonNode4 getNPerson4ById(Long id) {
        return nPersonRelationship4Repository.findNPersonNode4ById(id);
    }

    public NPersonNode4 createNPerson4(NPersonNode4 nPersonNode4) {
        return nPersonRelationship4Repository.save(nPersonNode4);
    }

    public NPersonNode4List getNPerson4ListById(Long id) {
        return nPersonRelationship4ListRepository.findNPersonNode4ListById(id);
    }

    public NPersonNode4List createNPerson4List(NPersonNode4List nPersonNode4List) {
        return nPersonRelationship4ListRepository.save(nPersonNode4List);
    }

    //-------- OPTION 5
    public NPersonNode5 getNPerson5ById(Long id) {
        return nPersonRelationship5Repository.findNPersonNode5ById(id);
    }

    public NPersonNode5 createNPerson5List(NPersonNode5 nPersonNode5) {
        return nPersonRelationship5Repository.save(nPersonNode5);
    }

    public NPersonNode5List getNPerson5ListById(Long id) {
        return nPersonRelationship5ListRepository.findNPersonNode5ListById(id);
    }

    public NPersonNode5List createNPerson5List(NPersonNode5List nPersonNode5) {
        return nPersonRelationship5ListRepository.save(nPersonNode5);
    }
}

