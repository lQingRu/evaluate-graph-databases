package com.qingru.graph.domain.neo4j.common;

import com.qingru.graph.domain.neo4j.optionOne.NRelationship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

//TODO: Solely for client to create relationship
@Data
@AllArgsConstructor
public class NRelationshipData {

    @NonNull
    private Long fromPersonId;
    @NonNull
    private Long toPersonId;
    @NonNull
    private NRelationship relationship;
}
