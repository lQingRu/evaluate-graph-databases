package com.qingru.graph.arangoRepository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.qingru.graph.domain.arango.CommsNode;
import com.qingru.graph.domain.arango.PersonNode;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommsNodeRepository extends ArangoRepository<CommsNode, String> {
    Optional<CommsNode> findById(String id);
}
