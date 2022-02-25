package com.qingru.graph.neo4jRepository.optionFour;

import com.qingru.graph.domain.neo4j.optionFour.NPersonNode4;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

public interface NPersonRelationship4Repository extends Neo4jRepository<NPersonNode4, Long> {
    @Query("MATCH (fromPerson:person4)-[metadataRelations]-(toPerson:person4) WHERE ID(fromPerson)=$id RETURN fromPerson,collect(metadataRelations), collect(toPerson)")
    NPersonNode4 findNPersonNode4ById(@Param("id") Long id);
}
