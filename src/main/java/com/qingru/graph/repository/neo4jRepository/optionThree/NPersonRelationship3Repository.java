package com.qingru.graph.repository.neo4jRepository.optionThree;

import com.qingru.graph.domain.neo4j.optionThree.NPersonNode3;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NPersonRelationship3Repository extends Neo4jRepository<NPersonNode3, Long> {

    // Needs to use custom query instead of ORM's findNPersonNode3ById to prevent infinite loop
    // This however only returns just the person node, without relationships
    // To return with relationships, use findPersonRelationshipsWithDegree()
    @Query("MATCH (fromPerson:person3) WHERE ID(fromPerson)=$id RETURN fromPerson")
    NPersonNode3 findNPersonNode3ById(@Param("id") Long id);

    @Query("MATCH (p:person3) WHERE ID(p)=$personId" + " CALL apoc.path.expandConfig(p, {\n"
            + "\trelationshipFilter: \"metadataRelations\",\n" + "    minLevel: 1,\n"
            + "    maxLevel: $maxDegree\n" + ", uniqueness: \"RELATIONSHIP_GLOBAL\"})\n"
            + "YIELD path\n" + "WITH path\n" + "WITH apoc.path.elements(path) AS elements\n"
            + "UNWIND range(0, size(elements)-2) AS index\n" + "WITH elements, index\n"
            + "WHERE index %2 = 0\n"
            + "WITH distinct  elements[index] AS subject, elements[index+1] AS relationship, elements[index+2] AS object\n"
            + "RETURN subject, collect(relationship), collect(object)")
    List<NPersonNode3> findPersonRelationshipsWithDegree(@Param("personId") Long personId,
            @Param("maxDegree") int maxDegree);

    // Converts all to primitive types or arrays: [value in $sourceType | toString(value)]
    // or to handle in app-level
    @Query("MATCH (fromPerson:person3)\n" + "WHERE ID(fromPerson)=$fromPersonId \n"
            + "WITH fromPerson\n" + "MATCH (toPerson:person3)\n"
            + "WHERE ID(toPerson)=$toPersonId\n"
            + "MERGE (fromPerson)-[r:metadataRelations]->(toPerson)\n"
            + "SET r.closeness=$closeness, r.sourceType=$sourceType, "
            + "r.sourceDescription=$sourceDescription, r.sourceStartDate=$sourceStartDate, "
            + "r.relationshipType=$relationshipType RETURN distinct fromPerson, collect(r), collect(toPerson)")
    NPersonNode3 createOutgoingRelationship(@Param("fromPersonId") Long fromPersonId,
            @Param("toPersonId") Long toPersonId, @Param("closeness") String closeness,
            @Param("sourceType") List<String> sourceType,
            @Param("sourceDescription") List<String> sourceDescription,
            @Param("sourceStartDate") List<String> sourceStartDate,
            @Param("relationshipType") String relationshipType);
}
