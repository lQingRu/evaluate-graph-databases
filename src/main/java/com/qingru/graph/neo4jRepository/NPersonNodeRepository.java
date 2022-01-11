package com.qingru.graph.neo4jRepository;

import com.qingru.graph.domain.neo4j.NPersonNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NPersonNodeRepository extends Neo4jRepository<NPersonNode, Long> {

    Optional<NPersonNode> findNPersonNodesById(Long id);


}
