package com.qingru.graph.domain.neo4j.common;

import com.qingru.graph.domain.neo4j.NPersonRelationshipEdge;
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
    private String description;
    private int age;
    private String imageUrl;
    private String username;
    @Relationship
    private List<NPersonRelationshipEdge> relations;
}
