package com.qingru.graph.domain.neo4j.optionOne;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Getter
public class NFlattenedRelationshipEdge1 {
    @RelationshipId
    private Long id;

    @TargetNode
    private NSourceNode1 sourceNode;

    private String relationshipType;
    private String closeness;
    @NonNull
    private Long sourceId;

    public NFlattenedRelationshipEdge1(NSourceNode1 sourceNode, String relationshipType,
            String closeness, Long sourceId) {
        this.sourceNode = sourceNode;
        this.relationshipType = relationshipType;
        this.closeness = closeness;
        this.sourceId = sourceId;
    }
}
