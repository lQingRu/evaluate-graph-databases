package com.qingru.graph.domain.neo4j.optionFour;

import lombok.*;
import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.internal.InternalRelationship;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NodesAndRelationships {

    private List<InternalNode> nodes;
    private List<InternalRelationship> relationships;
}
