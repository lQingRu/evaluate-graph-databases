package com.qingru.graph.domain.neo4j;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NRelationshipNode {

    private String closeness;
    private List<String> sourceType;
    private List<String> sourceDescription;
    private List<LocalDateTime> sourceStartDate;
}
