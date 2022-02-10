package com.qingru.graph.domain.neo4j.optionThree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("person3")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NPersonNode3 {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private Integer age;
    private String description;
    private String imageUrl;
    @Relationship(type = "metadataRelations", direction = Relationship.Direction.OUTGOING)
    private List<NFlattenedRelationshipEdge3> outgoingRelationships;
    @Relationship(type = "metadataRelations", direction = Relationship.Direction.INCOMING)
    private List<NFlattenedRelationshipEdge3> incomingRelationships;
}
