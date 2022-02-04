package com.qingru.graph.neo4jRepository.optionTwo;

import com.qingru.graph.domain.neo4j.optionTwo.NPersonNode2;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NPersonRelationship2Repository extends Neo4jRepository<NPersonNode2, Long> {

    @Query("MATCH (fromPerson:person2)-[rel:metadataRelations]-(toPerson:person2) WHERE ID(fromPerson)=$id RETURN fromPerson, collect(rel), collect(toPerson)")
    Optional<NPersonNode2> findNPersonNode2ById2(@Param("id") Long id);

    // Problem is: Only 1 directional mapping relationship if we want to do mapping unless to indicate 2 relationship edge directions but caused infinite loop
    //    @Query("MATCH (fromPerson:person2)-[rel:metadataRelations*1..$maxDegree]-(toPerson:person2) WHERE ID(fromPerson)=$personId RETURN fromPerson, collect(rel), collect(toPerson)")
    //    NPersonNode2 findPersonRelationshipsWithDegree(@Param("personId") String personId,
    //            @Param("maxDegree") int maxDegree);

    // [TEST] to see if by returning object type would capture all relationships
    @Query("MATCH (fromPerson:person2)-[rel:metadataRelations*1..$maxDegree]-(toPerson:person2) WHERE ID(fromPerson)=$personId RETURN fromPerson, rel, toPerson")
    Object findPersonRelationshipsWithDegree(@Param("personId") String personId,
            @Param("maxDegree") int maxDegree);

    //    @Query("MATCH (u1)-[rel:metadataRelations]-(u2) WHERE ID(u1)=$personId RETURN {fromPerson: u1, toPerson: u2, relationshipType: rel.type}")
    //    List<Object> findAllPersonRelationships(@Param("personId") int personId);
}
