package com.qingru.graph.domain.arango;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.PersistentIndex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Document("person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PersistentIndex(fields = {"username", "id"})
public class PersonNode {

    @Id
    private String id;
    @ArangoId
    @JsonIgnore
    private String arangoId;

    private String username;
    private int age;
    private String description;

    // NOTE: This may cause circular relations - Infinite loop
    //    @Relations(edges = PersonRelationshipEdge.class, lazy = true)
    //    private List<PersonNode> relations;

    public PersonNode(String username, String description, int age) {
        this.description = description;
        this.username = username;
        this.age = age;
        //        this.relations = new ArrayList<>();
    }
}
