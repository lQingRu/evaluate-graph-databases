package com.qingru.graph.init;

import com.qingru.graph.repository.arangoRepository.PersonNodeRepository;
import com.qingru.graph.repository.arangoRepository.PersonRelationshipRepository;
import com.qingru.graph.domain.arango.PersonNode;
import com.qingru.graph.domain.arango.PersonRelationshipEdge;
import com.qingru.graph.domain.common.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

@Component
public class ArangoInitRunner implements CommandLineRunner {

    @Autowired
    PersonNodeRepository personNodeRepository;
    @Autowired
    PersonRelationshipRepository personRelationshipRepository;

    @Override
    public void run(String... args) throws Exception {
        personNodeRepository.deleteAll();
        personRelationshipRepository.deleteAll();

        List<PersonNode> personCollection = createPersonCollection();
        personNodeRepository.saveAll(personCollection);
        Iterable<PersonNode> allPersons = personNodeRepository.findAll();
        long personCount = StreamSupport
                .stream(Spliterators.spliteratorUnknownSize(allPersons.iterator(), 0), false)
                .count();
        System.out.println(
                String.format("A total of %s persons are persisted in the database", personCount));
        createPersonRelationshipEdgeCollection();
        Thread.sleep(500);
        Iterable<PersonRelationshipEdge> allRelations = personRelationshipRepository.findAll();
        long relationCount = StreamSupport
                .stream(Spliterators.spliteratorUnknownSize(allRelations.iterator(), 0), false)
                .count();
        System.out.println(String.format("A total of %s relations are persisted in the database",
                relationCount));
    }

    private List<PersonNode> createPersonCollection() {
        return List.of(new PersonNode("John Doe", "Main Character", 40, "",
                        List.of(new Skill("Kayaking", "Open-sea", 3, "Sports"),
                                new Skill("Baking", "Keto Desserts", 5, "Cooking"))),
                new PersonNode("Johnny", "John Doe's friend", 35, "",
                        List.of(new Skill("Baking", "All sort of desserts", 8, "Cooking"))),
                new PersonNode("Jane", "John's wife", 34, "",
                        List.of(new Skill("Dancing", "Jazz", 6, "Sports"))),
                new PersonNode("Robb", "Random Guy", 30, "", List.of()),
                new PersonNode("Bran", "Random Guy 2", 28, "", List.of()));
    }

    private void createPersonRelationshipEdgeCollection() {
        personNodeRepository.findByUsername("John Doe").ifPresent(john -> {
            personNodeRepository.findByUsername("Johnny").ifPresent(johnny -> {
                personNodeRepository.findByUsername("Jane").ifPresent(
                        jane -> personRelationshipRepository.saveAll(
                                List.of(new PersonRelationshipEdge(john, johnny),
                                        new PersonRelationshipEdge(john, jane),
                                        new PersonRelationshipEdge(jane, johnny))));
                personNodeRepository.findByUsername("Bran").ifPresent(
                        bran -> personRelationshipRepository
                                .saveAll(List.of(new PersonRelationshipEdge(bran, john))));
                personNodeRepository.findByUsername("Robb").ifPresent(
                        robb -> personRelationshipRepository
                                .saveAll(List.of(new PersonRelationshipEdge(johnny, robb))));
            });
        });
    }
}
