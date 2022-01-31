package com.qingru.graph.neo4jRepository;

import com.qingru.graph.domain.neo4j.optionOne.NPersonNode1;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NPersonRelationship1Repository extends Neo4jRepository<NPersonNode1, Long> {


    //-------- OPTION 1
    // This is similar to:     @Query("MATCH (p:person1)-[r:metadataRelations]->(s:source) WHERE id(p) = $id RETURN p, collect(r), collect(s)")
    Optional<NPersonNode1> findNPersonNode1ById(@Param("id") Long id);

    // TODO: Add an identifier for relationship where the identifier is explicitly generated so
    // that both persons share the same id to filter for the same relationship (here we use closeness first)
    @Query("MATCH p=(toPerson:person1)-[r1:metadataRelations]-(source:source)-[r2:metadataRelations]-(fromPerson:person1) WHERE r1.closeness=r2.closeness AND id(toPerson)=$id RETURN {\n"
            + "    toPerson: properties(toPerson),\n" + "    fromPerson: properties(fromPerson),\n"
            + "   relationshipMetadata: {\n" + "        closeness:r1.closeness,\n"
            + "        source:{\n" + "            type:source.type,\n"
            + "            description:source.description\n" + "        }\n" + "    } }")
    Optional<Object> findPersonAndRelationshipByPersonId(@Param("id") Long id);

    @Query("MATCH (p:person1)-[r:metadataRelations]->(s:source) WHERE id(s) = $sourceId AND id(r)=$relationshipId RETURN p, collect(r), collect(s)")
    Optional<NPersonNode1> findPersonAndRelationshipBySourceIdAndRelationshipId(
            @Param("sourceId") Long sourceId, @Param("relationshipId") Long relationshipId);
}
