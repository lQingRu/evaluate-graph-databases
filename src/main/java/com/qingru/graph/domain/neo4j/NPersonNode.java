package com.qingru.graph.domain.neo4j;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("person")
@Builder
@Setter
@Getter
public class NPersonNode {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private int age;
    private String description;
    private String imageUrl;
    //    private List<Skill> skills;
    @Relationship
    private List<NPersonRelationshipEdge> relations;
}
