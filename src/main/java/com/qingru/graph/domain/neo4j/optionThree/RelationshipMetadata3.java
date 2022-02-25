package com.qingru.graph.domain.neo4j.optionThree;

import com.qingru.graph.domain.neo4j.common.Source;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RelationshipMetadata3 {

    private String closeness;
    private String type;
    private List<Source> source;
}
