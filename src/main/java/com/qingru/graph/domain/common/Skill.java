package com.qingru.graph.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Skill {

    private String skill;
    private String description;
    private int strength;
    private String type;

}
