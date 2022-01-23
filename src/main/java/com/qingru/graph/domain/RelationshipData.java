package com.qingru.graph.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

//TODO: Solely for client to create relationship
@Data
@AllArgsConstructor
public class RelationshipData {

    @NonNull
    private Long fromPersonId;
    @NonNull
    private Long toPersonId;
    @NonNull
    private String relationshipType;
    private Relationship relationship;
}
