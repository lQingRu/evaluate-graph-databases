package com.qingru.graph.domain.neo4j;

import com.qingru.graph.domain.neo4j.common.NPersonNode;
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

    //    // Neo4j does not allow nested attributes
    //    private Relationship relationship;

    public NPersonRelationshipEdge(NPersonNode person, String type) {
        this.person = person;
        this.type = type;
    }

}
