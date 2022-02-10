package com.qingru.graph.domain.neo4j.optionThree;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RelationshipProperties
@Getter
@Builder
public class NFlattenedRelationshipEdge3 {

    @RelationshipId
    private Long id;
    private String relationshipType;
    private String closeness;

    @TargetNode
    private NPersonNode3 targetPersonNode;

    // Nested attributes of Source flattened
    // Here we assume a relationship have more than 1 source
    private List<String> sourceType;
    private List<String> sourceDescription;

    // "Neo4j Cannot coerce STRING to LocalDateTime", hence need to store as string and then convert to LocalDateTime
    @JsonIgnore
    private List<String> sourceStartDate;

    public List<LocalDateTime> getSourceStartDateLDT() {
        return sourceStartDate.stream().map(date -> date != "" ? LocalDateTime.parse(date) : null)
                .collect(Collectors.toList());
    }

}
