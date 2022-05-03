package com.qingru.graph.repository.arangoRepository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.qingru.graph.domain.arango.PersonNode;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonNodeRepository extends ArangoRepository<PersonNode, String> {
    Optional<PersonNode> findByUsername(String username);
}
