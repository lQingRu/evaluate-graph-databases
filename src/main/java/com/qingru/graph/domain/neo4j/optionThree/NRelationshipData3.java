package com.qingru.graph.domain.neo4j.optionThree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class NRelationshipData3 {

    @NonNull
    private Long fromPersonId;
    @NonNull
    private Long toPersonId;
    @NonNull
    private String relationshipType;
    private RelationshipMetadata3 relationshipMetadata;
}
