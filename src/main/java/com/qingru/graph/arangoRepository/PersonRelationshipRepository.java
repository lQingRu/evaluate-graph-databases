package com.qingru.graph.arangoRepository;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.qingru.graph.domain.arango.PersonNode;
import com.qingru.graph.domain.arango.PersonRelationshipEdge;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRelationshipRepository
        extends ArangoRepository<PersonRelationshipEdge, String> {

    //    @Query("FOR person IN ANY CONCAT('person/',@personId) personRelationship RETURN person")
    @Query("FOR relation in personRelationship FILTER relation._from==CONCAT('person/',@personId) || relation._to==CONCAT('person/',@personId) RETURN relation")
    List<PersonNode> findAllPersonRelationships(@Param("personId") String personId);

    @Query("FOR person IN 1..@maxDegree ANY CONCAT('person/',@personId) personRelationship RETURN person")
    List<PersonNode> findPersonRelationshipsWithDegree(@Param("personId") String personId,
            @Param("maxDegree") int maxDegree);
}
