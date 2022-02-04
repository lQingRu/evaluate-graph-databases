package com.qingru.graph.neo4jRepository.optionOne;

import com.qingru.graph.domain.neo4j.optionOne.NSourceNode1;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface NSource1Repository extends Neo4jRepository<NSourceNode1, Long> {


    //-------- OPTION 1
    Optional<NSourceNode1> findNSourceNodeById(Long id);
}
