package com.qingru.graph.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelationshipHistory {

    private String closeness;
    private String description;
    private int typeId;
    private LocalDateTime started;
    private LocalDateTime ended;

}
