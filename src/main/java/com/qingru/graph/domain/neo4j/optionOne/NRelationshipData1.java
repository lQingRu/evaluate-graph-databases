package com.qingru.graph.domain.neo4j.optionOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class NRelationshipData1 {

    @NonNull
    private Long fromPersonId;
    @NonNull
    private Long toPersonId;
    @NonNull
    private NRelationship relationship;
}
