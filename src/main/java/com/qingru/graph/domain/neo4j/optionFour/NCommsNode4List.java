package com.qingru.graph.domain.neo4j.optionFour;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("comms4")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NCommsNode4List {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String value;
    private String type;
    @Relationship(type = "commsRelations", direction = Relationship.Direction.OUTGOING)
    private List<NCommsEdge4> outgoingRelationships;
    @Relationship(type = "commsRelations", direction = Relationship.Direction.INCOMING)
    private List<NCommsEdge4> incomingRelationships;
}
