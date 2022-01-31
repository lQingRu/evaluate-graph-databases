package com.qingru.graph.neo4jRepository;

import com.qingru.graph.domain.neo4j.optionOne.NSourceNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface NSource1Repository extends Neo4jRepository<NSourceNode, Long> {


    //-------- OPTION 1
    Optional<NSourceNode> findNSourceNodeById(Long id);
}
