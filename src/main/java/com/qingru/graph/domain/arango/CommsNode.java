package com.qingru.graph.domain.arango;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.PersistentIndex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qingru.graph.domain.neo4j.optionFour.NCommsEdge4;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Document("comms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommsNode {

    @Id
    private String id;
    @ArangoId
    @JsonIgnore
    private String arangoId;

    private String name;
    private String value;
    private String type;

    public CommsNode(String name, String value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }
}
