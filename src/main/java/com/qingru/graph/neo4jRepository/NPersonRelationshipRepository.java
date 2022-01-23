package com.qingru.graph.neo4jRepository;

import com.qingru.graph.domain.neo4j.NPersonNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NPersonRelationshipRepository extends Neo4jRepository<NPersonNode, Long> {

    Optional<NPersonNode> findNPersonNodesById(Long id);

    @Query("MATCH (user:person)-[rel:RELATIONS*1..$maxDegree]-() WHERE ID(user)=$personId RETURN rel")
    List<Object> findPersonRelationshipsWithDegree(@Param("personId") String personId,
            @Param("maxDegree") int maxDegree);

    @Query("MATCH (u1)-[rel:RELATIONS]-(u2) WHERE ID(u1)=$personId RETURN {fromPerson: u1, toPerson: u2, relationshipType: rel.type}")
    List<Object> findAllPersonRelationships(@Param("personId") int personId);

    // This has some issues, "No converter found capable of converting from type (...) to type [org.neo4j.driver.Value]
    // ---> taking in as pojo and accessing attributes seem to be not allowed in Neo4j
    //    @Query("MATCH (p)-[rel:RELATIONS]-() WHERE id(rel)=$relationshipId SET rel.type=$relationship.relationshipType"
    //            + "RETURN rel")
    @Query("MATCH (p)-[rel:RELATIONS]-() WHERE id(rel)=$relationshipId SET rel.type=$relationshipType")
    void updatePersonRelationship(@Param("relationshipId") int relationshipId,
            @Param("relationshipType") String relationshipType);

}
