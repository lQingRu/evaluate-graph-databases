package com.qingru.graph.domain.neo4j.optionOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.LocalDateTime;

@Node("source")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NSourceNode {

    @Id
    @GeneratedValue
    private Long id;
    private String type;
    private String description;
    private LocalDateTime startDate;
}
