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
public class NRelationshipEdge4List {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private NPersonNode4List targetPersonNode;
    private String closeness;
    private String relationshipType;
    @CompositeProperty(converter = SourceListConverter.class)
    private List<Source> sources;

    public NRelationshipEdge4List(NPersonNode4List targetPersonNode, String closeness,
            String relationshipType, List<Source> sources) {
        this.targetPersonNode = targetPersonNode;
        this.closeness = closeness;
        this.relationshipType = relationshipType;
        this.sources = sources;
    }
}
