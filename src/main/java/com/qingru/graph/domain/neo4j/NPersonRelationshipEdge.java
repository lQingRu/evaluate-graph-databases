package com.qingru.graph.domain.neo4j;

import com.qingru.graph.domain.RelationshipHistory;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Getter
public class NPersonRelationshipEdge {

    @RelationshipId
    private Long id;

    @TargetNode
    private NPersonNode person;

    private String type;

    private RelationshipHistory relationshipHistory;

    public NPersonRelationshipEdge(NPersonNode person, String type,
            RelationshipHistory relationshipHistory) {
        this.person = person;
        this.type = type;
        this.relationshipHistory = relationshipHistory;
    }

}
