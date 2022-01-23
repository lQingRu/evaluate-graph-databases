package com.qingru.graph.domain.neo4j;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NPersonRelationship {

    public NPersonNode toPerson;
    public NPersonNode fromPerson;

    // Relationship properties
    private String relationshipType;
}
