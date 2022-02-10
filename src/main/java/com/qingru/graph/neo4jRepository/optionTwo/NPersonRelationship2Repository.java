package com.qingru.graph.neo4jRepository.optionTwo;

import com.qingru.graph.domain.neo4j.optionTwo.NPersonNode2;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NPersonRelationship2Repository extends Neo4jRepository<NPersonNode2, Long> {

    // Needs to use custom query instead of ORM's findNPersonNode2ById to prevent infinite loop
    // This however only returns just the person node, without relationships
    // To return with relationships, use findPersonRelationshipsWithDegree()
    @Query("MATCH (fromPerson:person2) WHERE ID(fromPerson)=$id RETURN fromPerson")
    NPersonNode2 findNPersonNode2ById(@Param("id") Long id);

    // Set uniqueness to RELATIONSHIP_GLOBAL prevents infinite loop and ensures all relationships are covered
    // For uniqueness types, see: https://neo4j.com/labs/apoc/4.4/graph-querying/expand-paths-config/#path-expander-paths-config-config-uniqueness
    @Query("MATCH (p:person2) WHERE ID(p)=$personId" + " CALL apoc.path.expandConfig(p, {\n"
            + "\trelationshipFilter: \"metadataRelations\",\n" + "    minLevel: 1,\n"
            + "    maxLevel: $maxDegree\n" + ", uniqueness: \"RELATIONSHIP_GLOBAL\"})\n"
            + "YIELD path\n" + "WITH path\n" + "WITH apoc.path.elements(path) AS elements\n"
            + "UNWIND range(0, size(elements)-2) AS index\n" + "WITH elements, index\n"
            + "WHERE index %2 = 0\n"
            + "WITH distinct  elements[index] AS subject, elements[index+1] AS relationship, elements[index+2] AS object\n"
            + "RETURN subject, collect(relationship), collect(object)")
    List<NPersonNode2> findPersonRelationshipsWithDegree(@Param("personId") Long personId,
            @Param("maxDegree") int maxDegree);

    // [ISSUE] Does not take in NFlattenedRelationshipEdge2 and access the fields, hence requires to pass in individually
    // [LIMITATION] Need to do full traversal to fully return TargetNode + Source relationships, consider returning relationship id instead
    // [CONSIDER] Using CREATE, then MERGE (merge existing or create) only for UPDATE Relationships
    @Query("MATCH (fromPerson:person2)\n" + "WHERE ID(fromPerson)=$fromPersonId \n"
            + "WITH fromPerson\n" + "MATCH (toPerson:person2)\n"
            + "WHERE ID(toPerson)=$toPersonId\n"
            + "MERGE (fromPerson)-[r:metadataRelations]->(toPerson)\n"
            + "SET r.closeness=$closeness, r.sourceType=$sourceType, "
            + "r.sourceDescription=$sourceDescription, r.sourceStartDate=$sourceStartDate, "
            + "r.relationshipType=$relationshipType RETURN distinct fromPerson, collect(r), collect(toPerson)")
    NPersonNode2 createOutgoingRelationship(@Param("fromPersonId") Long fromPersonId,
            @Param("toPersonId") Long toPersonId, @Param("closeness") String closeness,
            @Param("sourceType") String sourceType,
            @Param("sourceDescription") String sourceDescription,
            @Param("sourceStartDate") LocalDateTime sourceStartDate,
            @Param("relationshipType") String relationshipType);

}
