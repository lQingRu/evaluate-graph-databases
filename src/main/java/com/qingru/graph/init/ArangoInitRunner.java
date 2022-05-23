package com.qingru.graph.init;

import com.qingru.graph.arangoRepository.PersonNodeRepository;
import com.qingru.graph.arangoRepository.PersonRelationshipRepository;
import com.qingru.graph.domain.arango.PersonNode;
import com.qingru.graph.domain.arango.PersonRelationshipEdge;
import com.qingru.graph.domain.common.RelationshipMetadata;
import com.qingru.graph.domain.neo4j.common.Source;
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
        return List.of(new PersonNode("Janice", "Blond girl", 33),
                new PersonNode("Tim", "Blond guy", 40), new PersonNode("Hally", "Cheerful", 33),
                new PersonNode("Jess", "Cheerful", 33));
    }

    private void createPersonRelationshipEdgeCollection() {
        personNodeRepository.findByUsername("Janice").ifPresent(janice -> {
            personNodeRepository.findByUsername("Tim").ifPresent(tim -> {
                personNodeRepository.findByUsername("Jess").ifPresent(jess -> {
                    personNodeRepository.findByUsername("Hally").ifPresent(hally -> {
                        personRelationshipRepository.saveAll(
                                List.of(new PersonRelationshipEdge(janice, tim, "sister_of",
                                                new RelationshipMetadata("very_close", "family",
                                                        new Source("family", "bloodline", null))),
                                        new PersonRelationshipEdge(janice, jess, "friend_of",
                                                new RelationshipMetadata("close", "school_friend",
                                                        new Source("social_media",
                                                                "facebook friend list", null)))));
                        personRelationshipRepository.saveAll(
                                List.of(new PersonRelationshipEdge(jess, janice, "friend_of",
                                        new RelationshipMetadata("close", "school_friend",
                                                new Source("social_media", "facebook friend list",
                                                        null)))));
                        personRelationshipRepository.saveAll(
                                List.of(new PersonRelationshipEdge(tim, janice, "brother_of",
                                        new RelationshipMetadata("very_close", "family",
                                                new Source("family", "bloodline", null)))));
                        personRelationshipRepository.saveAll(
                                List.of(new PersonRelationshipEdge(hally, jess, "friend_of",
                                        new RelationshipMetadata("very_close", "childhood_friend",
                                                new Source("social_media", "facebook wall post",
                                                        null)))));
                        personRelationshipRepository.saveAll(
                                List.of(new PersonRelationshipEdge(jess, hally, "friend_of",
                                        new RelationshipMetadata("very_close", "childhood_friend",
                                                new Source("social_media", "facebook wall post",
                                                        null)))));
                    });
                });
            });
        });
    }
}
