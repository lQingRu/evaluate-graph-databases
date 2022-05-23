package com.qingru.graph.arangoRepository;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.qingru.graph.domain.arango.CommsRelationshipEdge;
import com.qingru.graph.domain.arango.PersonRelationshipEdge;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommsRelationshipRepository
        extends ArangoRepository<CommsRelationshipEdge, String> {

    // Finds all direct relationships
    @Query("FOR relation in commsRelationship FILTER relation._from==CONCAT('comms/',@commsId) || relation._to==CONCAT('comms/',@commsId) RETURN relation")
    List<CommsRelationshipEdge> findAllCommsRelationships(@Param("commsId") String commsId);

    @Query("FOR v,e,p IN 1..@maxDegree ANY CONCAT('comms/',@commsId) commsRelationship RETURN DISTINCT e")
    List<CommsRelationshipEdge> findAllCommsRelationshipsWithDegree(@Param("commsId") String commsId, @Param("maxDegree") int maxDegree);

}
