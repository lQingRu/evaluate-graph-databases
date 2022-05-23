package com.qingru.graph.controller;

import com.qingru.graph.domain.arango.CommsNode;
import com.qingru.graph.domain.arango.PersonNode;
import com.qingru.graph.domain.arango.PersonRelationshipEdge;
import com.qingru.graph.domain.neo4j.common.NRelationshipData;
import com.qingru.graph.domain.neo4j.optionFive.NPersonNode5;
import com.qingru.graph.domain.neo4j.optionFive.NPersonNode5List;
import com.qingru.graph.domain.neo4j.optionFour.*;
import com.qingru.graph.domain.neo4j.optionOne.NPersonNode1;
import com.qingru.graph.domain.neo4j.optionOne.NRelationshipData1;
import com.qingru.graph.domain.neo4j.optionThree.NPersonNode3;
import com.qingru.graph.domain.neo4j.optionThree.NRelationshipData3;
import com.qingru.graph.domain.neo4j.optionTwo.NPersonNode2;
import com.qingru.graph.service.CommsService;
import com.qingru.graph.service.PersonService;
import com.qingru.graph.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comms")
public class CommsController {

    @Autowired
    CommsService commsService;

    @PostMapping("/arango")
    public CommsNode createComms(@RequestBody CommsNode commsNode) {
        return commsService.createCommsNode(commsNode);
    }

    @GetMapping("/arango/{id}")
    public CommsNode getRelationshipsByCommsId(@PathVariable("id") String id) {
        return commsService.getCommsNode(id);
    }

    //-------- OPTION 4
    @PostMapping("/neo4j/v4.3")
    public NCommsNode4List createNComms4(@RequestBody NCommsNode4List nCommsNode4List) {
        return commsService.createNCommsNode4(nCommsNode4List);
    }

    @GetMapping("/neo4j/v4.3/{id}")
    public NCommsNode4List getNRelationshipsByCommsId4(@PathVariable("id") Long id) {
        return commsService.getNCommsNode4(id);
    }
}
