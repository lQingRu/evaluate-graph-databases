package com.qingru.graph.domain.neo4j.optionFour;

import com.qingru.graph.domain.neo4j.common.Source;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.springframework.data.neo4j.core.convert.Neo4jConversionService;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyToMapConverter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class SourceConverter implements Neo4jPersistentPropertyToMapConverter<String, Source> {
    @Override
    public Map<String, Value> decompose(Source source,
            Neo4jConversionService neo4jConversionService) {
        Map<String, Value> mapToStoreInNeo4j = new HashMap<>();
        if (source == null) {
            return mapToStoreInNeo4j;
        }
        mapToStoreInNeo4j.put("type", Values.value(source.getType()));
        mapToStoreInNeo4j.put("description", Values.value(source.getDescription()));
        mapToStoreInNeo4j.put("startDate", Values.value(source.getStartDate()));
        return mapToStoreInNeo4j;
    }

    @Override
    public Source compose(Map<String, Value> mapFromNeo4j,
            Neo4jConversionService neo4jConversionService) {
        String type = String.valueOf(mapFromNeo4j.get("type"));
        String description = String.valueOf(mapFromNeo4j.get("description"));
        LocalDateTime startDate = mapFromNeo4j.get("startDate").asLocalDateTime();
        Source source = new Source(type, description, startDate);
        return source;
    }
}
