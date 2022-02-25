package com.qingru.graph.domain.neo4j.optionFive;

import com.qingru.graph.domain.neo4j.common.Source;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashMap;
import java.util.Map;


@RelationshipProperties
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NRelationshipEdge5 {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private NPersonNode5 targetPersonNode;
    private String closeness;
    private String relationshipType;
    @CompositeProperty
    private Map<String, String> source;

    public NRelationshipEdge5(NPersonNode5 targetPersonNode, String closeness,
            String relationshipType, Source source) {
        this.targetPersonNode = targetPersonNode;
        this.closeness = closeness;
        this.relationshipType = relationshipType;
        this.source = createSourceMap(source);
    }

    private Map<String, String> createSourceMap(Source source) {
        Map<String, String> sourceMap = new HashMap<>();
        sourceMap.put("type", source.getType());
        sourceMap.put("description", source.getDescription());

        // Cannot convert from LocalDateTime to Value (of map, i.e. even Object also cannot) format
        // --> 2022-02-25 14:40:02.507  WARN 4064 --- [nio-8080-exec-2] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: LOCAL_DATE_TIME is not iterable; nested exception is com.fasterxml.jackson.databind.JsonMappingException: LOCAL_DATE_TIME is not iterable (through reference chain: com.qingru.graph.domain.neo4j.optionFive.NPersonNode5["outgoingRelationships"]->java.util.ArrayList[0]->com.qingru.graph.domain.neo4j.optionFive.NRelationshipEdge5["source"]->java.util.HashMap["startDate"]->org.neo4j.driver.internal.value.LocalDateTimeValue["empty"])]
        //        sourceMap.put("startDate", Values.value(source.getStartDate()));
        return sourceMap;
    }
}
