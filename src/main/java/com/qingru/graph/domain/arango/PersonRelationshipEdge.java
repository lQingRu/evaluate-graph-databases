package com.qingru.graph.domain.arango;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import com.qingru.graph.domain.common.RelationshipMetadata;
import lombok.*;
import org.springframework.data.annotation.Id;

@Edge("personRelationship")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonRelationshipEdge {

    @Id
    private String id;
    @From
    private PersonNode fromPersonNode;

    @To
    private PersonNode toPersonNode;

    private String type;

    private RelationshipMetadata relationshipMetadata;

    public PersonRelationshipEdge(PersonNode fromPersonNode, PersonNode toPersonNode) {
        this.fromPersonNode = fromPersonNode;
        this.toPersonNode = toPersonNode;
    }

    @Override
    public String toString() {
        return "PersonRelationship{" + "id='" + id + '\'' + ", fromPerson=" + fromPersonNode
                + ", toPerson=" + toPersonNode + ", type='" + type + '\'' + '}';
    }

}
