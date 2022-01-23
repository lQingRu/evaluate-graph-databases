package com.qingru.graph.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Source {
    private String type;
    private String description;
    private LocalDateTime startDate;
}
