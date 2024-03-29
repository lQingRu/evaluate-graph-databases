package com.qingru.graph.domain.neo4j.optionFour;

import com.qingru.graph.domain.neo4j.common.Source;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NRelationshipEdge4 {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private NPersonNode4 targetPersonNode;
    private String closeness;
    private String relationshipType;
    @CompositeProperty(converter = SourceConverter.class)
    private Source source;

    public NRelationshipEdge4(NPersonNode4 targetPersonNode, String closeness,
            String relationshipType, Source source) {
        this.targetPersonNode = targetPersonNode;
        this.closeness = closeness;
        this.relationshipType = relationshipType;
        this.source = source;
    }
}
