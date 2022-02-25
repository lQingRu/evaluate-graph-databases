package com.qingru.graph.domain.neo4j.optionFive;

import com.qingru.graph.domain.neo4j.common.Source;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RelationshipProperties
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NRelationshipEdge5List {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private NPersonNode5List targetPersonNode;
    private String closeness;
    private String relationshipType;

    // Out-of-the-box, does not convert List<String> / List<Value> properly
    @CompositeProperty
    private Map<String, List<String>> source;

    public NRelationshipEdge5List(NPersonNode5List targetPersonNode, String closeness,
            String relationshipType, List<Source> sourceList) {
        this.targetPersonNode = targetPersonNode;
        this.closeness = closeness;
        this.relationshipType = relationshipType;
        this.source = createSourceMap(sourceList);
    }

    private Map<String, List<String>> createSourceMap(List<Source> sourceList) {
        Map<String, List<String>> sourceMapList = new HashMap<>();
        List<String> types = sourceList.stream().map(Source::getType).collect(Collectors.toList());
        List<String> descriptions =
                sourceList.stream().map(Source::getDescription).collect(Collectors.toList());
        sourceMapList.put("types", types);
        sourceMapList.put("descriptions", descriptions);
        return sourceMapList;
    }
}
