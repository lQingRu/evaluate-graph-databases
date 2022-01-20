package com.qingru.graph.neo4jRepository;

import com.qingru.graph.domain.neo4j.NPersonNode;
import com.qingru.graph.domain.neo4j.NPersonRelationshipEdge;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NPersonNodeRepository extends Neo4jRepository<NPersonNode, Long> {

    Optional<NPersonNode> findNPersonNodesById(Long id);

    @Query("MATCH ()-[r:RELATIONS*1..@maxDegree]-(toUser:person) WHERE toUser.username=@username RETURN DISTINCT r")
    List<NPersonRelationshipEdge> findPersonRelationshipsWithDegree(
            @Param("username") String username, @Param("maxDegree") int maxDegree);

}
