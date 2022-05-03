package com.qingru.graph.repository.arangoRepository;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.qingru.graph.domain.arango.PersonRelationshipEdge;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRelationshipRepository
        extends ArangoRepository<PersonRelationshipEdge, String> {

    // Finds all direct relationships
    @Query("FOR relation in personRelationship FILTER relation._from==CONCAT('person/',@personId) || relation._to==CONCAT('person/',@personId) RETURN relation")
    List<PersonRelationshipEdge> findAllPersonRelationships(@Param("personId") String personId);

    // More complex traversals can be done through Graphs (i.e. Graphs need to be first created)
    //-->  @Query("FOR person IN person FILTER person._key==@personId FOR v, e, p IN 1..@maxDegree ANY person GRAPH 'relationshipGraph' RETURN e")
    @Query("FOR v,e,p IN 1..@maxDegree ANY CONCAT('person/',@personId) personRelationship RETURN DISTINCT e")
    List<PersonRelationshipEdge> findPersonRelationshipsWithDegree(
            @Param("personId") String personId, @Param("maxDegree") int maxDegree);

    @Query("FOR relation in personRelationship FILTER relation._from==CONCAT('person/',@personId) || relation._to==CONCAT('person/',@personId) REMOVE {_key: relation._key} IN personRelationship")
    void deletePersonRelationshipsByPersonId(@Param("personId") String personId);
}
