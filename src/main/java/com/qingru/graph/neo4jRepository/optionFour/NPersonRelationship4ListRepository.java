package com.qingru.graph.neo4jRepository.optionFour;

import com.qingru.graph.domain.neo4j.optionFour.NPersonNode4List;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NPersonRelationship4ListRepository
        extends Neo4jRepository<NPersonNode4List, Long> {
    @Query("MATCH (fromPerson:person4List)-[metadataRelations]-(toPerson:person4List) WHERE ID(fromPerson)=$id RETURN fromPerson, collect(metadataRelations), collect(toPerson)")
    NPersonNode4List findNPersonNode4ListById(@Param("id") Long id);

    @Query("MATCH (p:person4List)\n" + "WHERE ID(p)=$id\n" + "CALL apoc.path.expandConfig(p, {\n"
            + "relationshipFilter: \"metadataRelations\",\n" + "minLevel:1,\n"
            + "maxLevel: $degree,\n" + "uniqueness: \"RELATIONSHIP_GLOBAL\" \n" + "})\n"
            + "YIELD path\n" + "WITH apoc.path.elements(path) AS elements \n"
            + "UNWIND range(0, size(elements)-2) AS index \n" + "WITH elements, index\n"
            + "WHERE index %2 = 0 \n"
            + "WITH distinct  elements[index] AS subject, elements[index+1] AS predicate, elements[index+2] AS object \n"
            + "RETURN subject, collect(predicate), collect(object)\n")
    List<NPersonNode4List> findPersonRelationshipsWithDegree(@Param("id") Long id,
            @Param("degree") int degree);


    @Query("MATCH (p:person4List)\n" + "WHERE ID(p)=$id\n" + "CALL apoc.path.expandConfig(p, {\n"
            + "relationshipFilter: \"metadataRelations\",\n" + "minLevel:1,\n" + "maxLevel: -1,\n"
            + "uniqueness: \"RELATIONSHIP_GLOBAL\" \n" + "})\n" + "YIELD path\n"
            + "WITH apoc.path.elements(path) AS elements \n"
            + "UNWIND range(0, size(elements)-2) AS index \n" + "WITH elements, index\n"
            + "WHERE index %2 = 0 \n"
            + "WITH distinct  elements[index] AS subject, elements[index+1] AS predicate, elements[index+2] AS object \n"
            + "RETURN subject, collect(predicate), collect(object)\n")
    List<NPersonNode4List> findAllPersonRelationships(@Param("id") Long id);
}
