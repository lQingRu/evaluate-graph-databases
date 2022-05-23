package com.qingru.graph.domain.neo4j.optionFour;

import com.qingru.graph.domain.neo4j.optionThree.RelationshipMetadata3;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class NCommsRelationshipData4 {

    private Long fromPersonId;
    private Long commsId;
    private Long toPersonId;
    @NonNull
    private String relationshipType;
    private RelationshipMetadata3 relationshipMetadata;
}
