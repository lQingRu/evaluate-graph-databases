package com.qingru.graph.domain.neo4j.optionFour;

import com.qingru.graph.domain.neo4j.common.Source;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.springframework.data.neo4j.core.convert.Neo4jConversionService;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyToMapConverter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Assumption: All fields in Source must be filled for simplicity of POC
public class SourceListConverter
        implements Neo4jPersistentPropertyToMapConverter<String, List<Source>> {

    @Override
    public Map<String, Value> decompose(List<Source> sources,
            Neo4jConversionService neo4jConversionService) {
        Map<String, Value> mapToStoreInNeo4j = new HashMap<>();
        if (sources == null || sources.isEmpty()) {
            return mapToStoreInNeo4j;
        }
        mapToStoreInNeo4j.put("types",
                Values.value(sources.stream().map(Source::getType).collect(Collectors.toList())));
        mapToStoreInNeo4j.put("descriptions", Values.value(
                sources.stream().map(Source::getDescription).collect(Collectors.toList())));
        mapToStoreInNeo4j.put("startDates", Values.value(
                sources.stream().map(Source::getStartDate).collect(Collectors.toList())));
        return mapToStoreInNeo4j;
    }

    @Override
    public List<Source> compose(Map<String, Value> mapFromNeo4j,
            Neo4jConversionService neo4jConversionService) {
        if (mapFromNeo4j.isEmpty()) {
            return List.of();
        }
        List<String> typeList = mapFromNeo4j.get("types").asList(type -> type.asString());
        List<String> descriptionList =
                mapFromNeo4j.get("descriptions").asList(description -> description.asString());
        List<LocalDateTime> localDateTimeList =
                mapFromNeo4j.get("startDates").asList(dateTime -> dateTime.asLocalDateTime());

        List<Source> sources = new ArrayList<>();
        for (int i = 0; i < typeList.size(); i++) {
            Source source =
                    new Source(typeList.get(i), descriptionList.get(i), localDateTimeList.get(i));
            sources.add(source);
        }
        return sources;
    }
}
