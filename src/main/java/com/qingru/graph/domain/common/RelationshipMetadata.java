package com.qingru.graph.domain.common;

import com.qingru.graph.domain.neo4j.common.Source;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RelationshipMetadata {

    private String closeness;
    private String type;
    private Source source; // How does person A and person B know each other?
}
