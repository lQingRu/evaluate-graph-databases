package com.qingru.graph.domain.neo4j.optionFour;

import com.qingru.graph.domain.neo4j.common.Source;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@RelationshipProperties
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NPersonCommsEdge4 {

    @Id
    @GeneratedValue
    private Long id;


    // person -> comms
    @TargetNode
    private NCommsNode4List targetCommsNode;
    private String closeness;
    private String relationshipType;
    @CompositeProperty(converter = SourceConverter.class)
    private List<Source>  sources;

    public NPersonCommsEdge4(NCommsNode4List targetCommsNode, String closeness,
            String relationshipType, List<Source> sources) {
        this.targetCommsNode = targetCommsNode;
        this.closeness = closeness;
        this.relationshipType = relationshipType;
        this.sources = sources;
    }
}
