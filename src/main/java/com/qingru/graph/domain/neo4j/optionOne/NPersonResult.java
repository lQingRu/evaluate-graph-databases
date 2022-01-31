package com.qingru.graph.domain.neo4j.optionOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NPersonResult {
    private String username;
    private Integer age;
    private String description;
    private String imageUrl;
}
