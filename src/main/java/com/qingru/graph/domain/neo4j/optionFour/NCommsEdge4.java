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
public class NCommsEdge4 {

    @Id
    @GeneratedValue
    private Long id;


    // comms -> person
    @TargetNode
    private NPersonNode4 targetPersonNode;
    private String closeness;
    private String relationshipType;
    @CompositeProperty(converter = SourceConverter.class)
    private List<Source> sources;

    public NCommsEdge4(NPersonNode4 targetPersonNode, String closeness,
            String relationshipType, List<Source> sources ) {
        this.targetPersonNode = targetPersonNode;
        this.closeness = closeness;
        this.relationshipType = relationshipType;
        this.sources = sources;
    }
}
