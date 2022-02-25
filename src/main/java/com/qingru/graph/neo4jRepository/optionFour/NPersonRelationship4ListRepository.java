package com.qingru.graph.neo4jRepository.optionFour;

import com.qingru.graph.domain.neo4j.optionFour.NPersonNode4List;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

public interface NPersonRelationship4ListRepository
        extends Neo4jRepository<NPersonNode4List, Long> {
    @Query("MATCH (fromPerson:person4List)-[metadataRelations]-(toPerson:person4List) WHERE ID(fromPerson)=$id RETURN fromPerson, collect(metadataRelations), collect(toPerson)")
    NPersonNode4List findNPersonNode4ListById(@Param("id") Long id);
}
