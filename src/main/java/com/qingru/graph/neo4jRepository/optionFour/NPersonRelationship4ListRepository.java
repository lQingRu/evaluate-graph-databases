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


    // Example of just getting direct relationships with filters
    //    @Query("MATCH (fp: person4List)-[r:metadataRelations]-(tp:person4List) WHERE r.closeness=\"very_close\" AND any(type in r.`sources.types` WHERE type IN [\"family\"]) RETURN fp, collect(r), collect(tp)")
    //    // --> explore https://neo4j.com/labs/apoc/4.1/overview/apoc.map/apoc.map.mget/
    //    // --> https://stackoverflow.com/questions/54363918/apoc-path-expandconfig-extract-subject-object-predicate-triplet
    //    // WHERE any(x IN categories WHERE x IN $allowedCategories)

    @Query("MATCH (p:person4List)\n" + "WHERE ID(p)=$id\n" + "CALL apoc.path.expandConfig(p, {\n"
            + "relationshipFilter: \"metadataRelations\",\n" + "minLevel:1,\n"
            + "maxLevel: $degree,\n" + "uniqueness: \"RELATIONSHIP_GLOBAL\" \n" + "})\n"
            + "YIELD path\n" + "WITH apoc.path.elements(path) AS elements \n"
            + "UNWIND range(0, size(elements)-2) AS index \n" + "WITH elements, index\n"
            + "WHERE index %2 = 0 \n"
            + "WITH distinct  elements[index] AS subject, elements[index+1] AS predicate, elements[index+2] AS object\n"
            + "WHERE\n" + "CASE WHEN NOT $closeness IS NULL\n"
            + "THEN properties(predicate).`closeness` IN $closeness\n" + "ELSE TRUE\n" + "END AND\n"
            + "CASE WHEN NOT $sourceTypes IS NULL\n"
            + "THEN any(type in properties(predicate).`sources.types` WHERE type IN $sourceTypes)\n"
            + "ELSE TRUE\n" + "END\n" + "RETURN subject, collect(predicate), collect(object)\n")
    List<NPersonNode4List> findPersonRelationshipsWithDegreeWithFilter(@Param("id") Long id,
            @Param("degree") int degree, @Param("closeness") List<String> closeness,
            @Param("sourceTypes") List<String> sourceTypes);
}
