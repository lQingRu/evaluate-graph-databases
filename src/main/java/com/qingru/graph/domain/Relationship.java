package com.qingru.graph.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Relationship {

    private String closeness;
    private Source source; // How does person A and person B know each other?
}
