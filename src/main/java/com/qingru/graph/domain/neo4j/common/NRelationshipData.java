package com.qingru.graph.domain.neo4j.common;

import com.qingru.graph.domain.RelationshipMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class NRelationshipData {

    @NonNull
    private Long fromPersonId;
    @NonNull
    private Long toPersonId;
    @NonNull
    private String relationshipType;
    private RelationshipMetadata relationshipMetadata;
}
