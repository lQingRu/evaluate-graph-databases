package com.qingru.graph.domain.arango;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import com.qingru.graph.domain.common.RelationshipMetadata;
import com.qingru.graph.domain.neo4j.optionThree.RelationshipMetadata3;
import lombok.*;
import org.springframework.data.annotation.Id;

@Edge("commsRelationship")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommsRelationshipEdge {

    @Id
    private String id;
    @From
    private CommsNode fromCommsNode;

    @To
    private PersonNode toPersonNode;

    private String type;

    private RelationshipMetadata3 relationshipMetadata;

    public CommsRelationshipEdge(CommsNode fromCommsNode, PersonNode toPersonNode, String type) {
        this.fromCommsNode = fromCommsNode;
        this.toPersonNode = toPersonNode;
        this.type=type;
    }

    public CommsRelationshipEdge(CommsNode fromCommsNode, PersonNode toPersonNode, String type,
            RelationshipMetadata3 relationshipMetadata) {
        this.fromCommsNode = fromCommsNode;
        this.toPersonNode = toPersonNode;
        this.type = type;
        this.relationshipMetadata = relationshipMetadata;
    }

}
