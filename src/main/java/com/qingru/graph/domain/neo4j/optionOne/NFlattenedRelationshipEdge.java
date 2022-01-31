package com.qingru.graph.domain.neo4j.optionOne;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Getter
public class NFlattenedRelationshipEdge {
    @RelationshipId
    private Long id;

    // Person node -> Source node
    @TargetNode
    private NSourceNode sourceNode;

    private String relationshipType;
    private String closeness;

    public NFlattenedRelationshipEdge(NSourceNode sourceNode, String relationshipType,
            String closeness) {
        this.sourceNode = sourceNode;
        this.relationshipType = relationshipType;
        this.closeness = closeness;
    }
}
