package com.qingru.graph.domain.neo4j.optionFour;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class NRelationshipEdge4ListFilter {

    private List<String> closeness;
    private List<String> relationshipTypes;
    private List<String> sourceTypes;
    private List<String> sourceDescriptions;
    private List<LocalDateTime> sourceStartDates;

    public NRelationshipEdge4ListFilter() {
        this.closeness = new ArrayList<>();
        this.relationshipTypes = new ArrayList<>();
        this.sourceTypes = new ArrayList<>();
        this.sourceDescriptions = new ArrayList<>();
        this.sourceStartDates = new ArrayList<>();
    }
}
