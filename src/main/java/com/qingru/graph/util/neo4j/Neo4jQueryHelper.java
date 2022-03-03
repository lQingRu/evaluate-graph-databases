package com.qingru.graph.util.neo4j;

import java.util.List;
import java.util.stream.Collectors;

// This can be made into a builder class
public class Neo4jQueryHelper {

    private static final String PREDICATE = "predicate";
    private static final String PROPERTIES = "properties";

    // Example: properties(predicate).`closeness` in ["very_close"]
    public static final String singleValuedEdgeFilter(String field, List<String> filters) {
        if (filters.isEmpty()) {
            return null;
        }
        return String.format("%s(%s).`%s` IN [%s]", PROPERTIES, PREDICATE, field,
                stringifyElementInList(filters));
    }

    // Example: any(type in properties(predicate).`sources.types` WHERE type IN["family"])
    public static final String multiValuedEdgeFilter(String field, List<String> filters) {
        if (filters.isEmpty()) {
            return null;
        }
        return String
                .format("any (value IN %s(%s).`%s` WHERE value IN [%s])", PROPERTIES, PREDICATE,
                        field, stringifyElementInList(filters));
    }

    private static String stringifyElementInList(List<String> list) {
        return list.stream().collect(Collectors.joining("','", "'", "'"));
    }
}
