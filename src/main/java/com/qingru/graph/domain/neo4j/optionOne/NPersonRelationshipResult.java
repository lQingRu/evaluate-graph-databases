package com.qingru.graph.domain.neo4j.optionOne;

import com.qingru.graph.domain.common.RelationshipMetadata;
import com.qingru.graph.domain.neo4j.common.Source;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class NPersonRelationshipResult {

    public NPersonResult toPerson;
    public NPersonResult fromPerson;

    // Nested attributes to be stored in here as well
    private RelationshipMetadata relationshipMetadata;

    public NPersonRelationshipResult(Map<String, Object> map) {
        if (!map.containsKey("fromPerson") && !map.containsKey("toPerson") && !map
                .containsKey("relationshipMetadata")) {
            throw new IllegalArgumentException(
                    "Input map to create NPersonRelationshipResult has insufficient attributes");
        }

        // Create toPerson
        Map<String, Object> toPersonMap = (Map) map.get("toPerson");
        NPersonResult fromPerson =
                NPersonResult.builder().username((String) (toPersonMap.get("username")))
                        .age(Integer.parseInt(toPersonMap.get("age").toString()))
                        .imageUrl((String) (toPersonMap.get("imageUrl")))
                        .description((String) (toPersonMap.get("description"))).build();

        // Create fromPerson
        Map<String, Object> fromPersonMap = (Map) map.get("fromPerson");
        NPersonResult toPerson =
                NPersonResult.builder().username((String) (fromPersonMap.get("username")))
                        .age(Integer.parseInt(fromPersonMap.get("age").toString()))
                        .imageUrl((String) (fromPersonMap.get("imageUrl")))
                        .description((String) (fromPersonMap.get("description"))).build();

        // Create relationshipMetadata
        Map<String, Object> relationshipMetadataMap = (Map) map.get("relationshipMetadata");
        Map<String, Object> sourceMap = (Map) relationshipMetadataMap.get("source");
        Source source =
                new Source((String) sourceMap.get("type"), (String) sourceMap.get("description"),
                        (LocalDateTime) sourceMap.get("startDate"));
        RelationshipMetadata relationshipMetadata = RelationshipMetadata.builder()
                .closeness((String) relationshipMetadataMap.get("closeness")).source(source)
                .type((String) relationshipMetadataMap.get("type")).build();

        this.fromPerson = fromPerson;
        this.toPerson = toPerson;
        this.relationshipMetadata = relationshipMetadata;
    }
}
