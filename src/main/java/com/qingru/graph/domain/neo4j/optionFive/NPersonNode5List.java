package com.qingru.graph.domain.neo4j.optionFive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("person5List")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NPersonNode5List {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private Integer age;
    private String description;
    private String imageUrl;
    @Relationship(type = "metadataRelations", direction = Relationship.Direction.OUTGOING)
    private List<NRelationshipEdge5List> outgoingRelationships;
    @Relationship(type = "metadataRelations", direction = Relationship.Direction.INCOMING)
    private List<NRelationshipEdge5List> incomingRelationships;
}
