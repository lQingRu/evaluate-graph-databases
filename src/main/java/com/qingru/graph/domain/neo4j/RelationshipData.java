package com.qingru.graph.domain.neo4j;

import com.qingru.graph.domain.RelationshipHistory;
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
    private RelationshipHistory relationshipHistory;
}
