package com.qingru.graph.neo4jRepository.optionFour;

import com.qingru.graph.domain.neo4j.optionFour.NCommsNode4List;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface NCommsRelationship4ListRepository
        extends Neo4jRepository<NCommsNode4List, Long> {
    @Query("MATCH (person:person4)-[commsRelations]-(toCommsNode:comms4) WHERE ID(toCommsNode)=$id RETURN toCommsNode, collect(commsRelations), collect(person)")
    NCommsNode4List findNCommsNode4ListById(@Param("id") Long id);

    // TODO: Unable to return this because there are more than 1 records, and Spring requires a mapper to resolve -> hence try use Neo4jClient directly then map manually
    @Query("MATCH p=(person:person4)-[commsRelations:commsRelations*1..10]-(toCommsNode:comms4) \n"
            + "WHERE ID(person) = $id\n" + "RETURN  nodes(p) as n, relationships(p) as rel")
    Iterable<Map<String, Object>> findTraversal(@Param("id") Long id);

    //    @Query("MATCH (startNode:comms4)\n" + "CALL apoc.path.subgraphNodes(startNode,\n"
//            + "  {maxLevel: -1, relationshipFilter: 'commsRelations'}) YIELD node AS personNodes\n"
//            + "CALL apoc.path.subgraphNodes(personNodes,\n"
//            + "  {maxLevel: -1, relationshipFilter: 'commsRelations'}) YIELD node AS \n"
//            + "personCommsNodes\n" + "WITH\n" + "    startNode,\n"
//            + "  collect(DISTINCT personNodes) AS directPersonCommsNodes,\n"
//            + "  collect(DISTINCT personCommsNodes) AS personCommsNodes\n"
//            + "WHERE ID(startNode)=$id\n"
//            + "RETURN startNode, directPersonCommsNodes, personCommsNodes;")
//    NCommsNode4List findNCommsNode4ListWithAllRelationsById(@Param("id") Long id);

    // ONLY RETURNED CommsNode because no relations returned
    @Query("MATCH (startNode:comms4)\n" + "CALL apoc.path.subgraphNodes(startNode,\n"
            + "  {maxLevel: -1, relationshipFilter: 'commsRelations'}) YIELD node AS personNodes\n"
            + "WITH\n" + "    startNode,\n"
            + "  collect(DISTINCT personNodes) AS directPersonCommsNodes\n"
            + "WHERE ID(startNode)=$id\n" + "RETURN startNode, directPersonCommsNodes;")
    NCommsNode4List findNCommsNode4ListWithAllRelationsById(@Param("id") Long id);

    // This will not work because the order of elements returned is not consistent at triple (:comms4)-[:commsRelations]-(:person4)
    @Query("MATCH (commsNode:comms4)\n" + "WHERE ID(commsNode)=$id\n"
            + "CALL apoc.path.expandConfig(commsNode, {\n"
            + "relationshipFilter: \"commsRelations\",\n" + "minLevel:1,\n" + "maxLevel: -1,\n"
            + "uniqueness: \"RELATIONSHIP_GLOBAL\" \n" + "})\n" + "YIELD path\n"
            + "WITH apoc.path.elements(path) AS elements \n"
            + "UNWIND range(0, size(elements)-2) AS index \n" + "WITH elements, index\n"
            + "WHERE index %2 = 0 \n"
            + "WITH distinct  elements[index] AS subject, elements[index+1] AS predicate, elements[index+2] AS object \n"
            + "RETURN subject, collect(predicate), collect(object)\n")
    List<NCommsNode4List> findNCommsNode4ListWithAllCommsRelationsById(@Param("id") Long id);


    // DOES NOT RETURN ALL THE COMMS INFO OBJECTS
    @Query("MATCH (commsNode: comms4)\n" + "WHERE ID(commsNode) = $id\n"
            + "CALL apoc.path.expandConfig(commsNode, {\n"
            + "    relationshipFilter: \"commsRelations\",\n" + "    minLevel: 1,\n"
            + "    maxLevel: 2,\n" + "    uniqueness: \"RELATIONSHIP_GLOBAL\"\n" + "})\n"
            + "YIELD path\n" + "WITH apoc.path.elements(path) AS elements, path\n"
            + "UNWIND range(0, size(elements)-2) AS index\n" + "WITH elements, index, path\n"
            + "WHERE index= 0\n"
            + "WITH distinct elements[index] as subject, elements[index+1] AS predicate,  elements[index+2..] AS object, path,elements, index\n"
            + "RETURN path, index, elements, subject, collect(predicate), object")
    List<NCommsNode4List> findNCommsNode4ListWithAllCommsRelationsById2(@Param("id") Long id);


    // 2 directional relationships merged to return all relationships in triple expansion of relationships
    @Query("MATCH (commsNode: comms4)\n" + "WHERE ID(commsNode) = $id\n"
            + "CALL apoc.path.expandConfig(commsNode, {\n" + "    minLevel: 1,\n"
            + "    maxLevel: 2,\n" + "    sequence: 'comms4,commsRelations,>person4',\n"
            + "    uniqueness: \"RELATIONSHIP_GLOBAL\"})\n" + "YIELD path\n"
            + "WITH apoc.path.elements(path) AS elements, path\n"
            + "UNWIND range(0, size(elements)-2) AS index\n" + "WITH elements, index, path\n"
            + "WHERE index%2=0\n"
            + "WITH distinct elements[index] as subject, elements[index+1] AS predicate,  elements[index+2] AS object, path,elements, index\n"
            + "RETURN subject,predicate, object, index, path\n" + "UNION\n"
            + "MATCH (commsNode: comms4)\n" + "WHERE ID(commsNode) = 28\n"
            + "CALL apoc.path.expandConfig(commsNode, {\n" + "    minLevel: 1,\n"
            + "    maxLevel: 2,\n" + "    labelFilter: '>comms4',\n"
            + "    uniqueness: \"RELATIONSHIP_GLOBAL\"})\n" + "YIELD path\n"
            + "WITH apoc.path.elements(path) AS elements, path\n"
            + "UNWIND range(0, size(elements)-2) AS index\n" + "WITH elements, index, path\n"
            + "WHERE index%2=0\n"
            + "WITH distinct elements[index] as object, elements[index+1] AS predicate,  elements[index+2] AS subject, path,elements, index\n"
            + "WHERE labels(object) = [\"person4\"]\n"
            + "RETURN subject,predicate, object, index, path")
    List<Object> findNCommsNode4ListWithAllCommsRelationsById3(@Param("id") Long id);


    // convert.to.tree will not work too because it returns a map then we'll need go construct the whole object ourselves
}
