package com.qingru.graph.domain.neo4j.optionTwo;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDateTime;

@RelationshipProperties
@Getter
@Builder
public class NFlattenedRelationshipEdge2 {

    @RelationshipId
    private Long id;
    private String relationshipType;

    // Person node -> Person node 2 (targetPersonNode)
    @TargetNode
    private NPersonNode2 targetPersonNode;

    private String closeness;

    // Nested attributes of Source flattened
    // Here we assume a relationship only have 1 source
    // TODO: Explore a relationship with more than 1 source
    private String sourceType;
    private String sourceDescription;
    private LocalDateTime sourceStartDate;
}
