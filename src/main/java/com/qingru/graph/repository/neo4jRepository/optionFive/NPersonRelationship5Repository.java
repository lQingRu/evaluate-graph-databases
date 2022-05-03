package com.qingru.graph.repository.neo4jRepository.optionFive;

import com.qingru.graph.domain.neo4j.optionFive.NPersonNode5;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

public interface NPersonRelationship5Repository extends Neo4jRepository<NPersonNode5, Long> {
    @Query("MATCH (fromPerson:person5)-[metadataRelations]-(toPerson:person5) WHERE ID(fromPerson)=$id RETURN fromPerson,collect(metadataRelations), collect(toPerson)")
    NPersonNode5 findNPersonNode5ById(@Param("id") Long id);
}
