package com.qingru.graph.domain.neo4j.optionOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NRelationship {

    // Flattened relationship attributes
    private String relationshipType;
    private String closeness;

    // Nested relationship attributes
    private NSourceNode source;

}
