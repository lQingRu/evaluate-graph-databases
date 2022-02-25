package com.qingru.graph.neo4jRepository.optionFive;

import com.qingru.graph.domain.neo4j.optionFive.NPersonNode5List;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

public interface NPersonRelationship5ListRepository
        extends Neo4jRepository<NPersonNode5List, Long> {
    @Query("MATCH (fromPerson:person5List)-[metadataRelations]-(toPerson:person5List) WHERE ID(fromPerson)=$id RETURN fromPerson,collect(metadataRelations), collect(toPerson)")
    NPersonNode5List findNPersonNode5ListById(@Param("id") Long id);
}
