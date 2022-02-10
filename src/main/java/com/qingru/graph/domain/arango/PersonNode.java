package com.qingru.graph.domain.arango;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.PersistentIndex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qingru.graph.domain.common.Skill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

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
    private String imageUrl;
    private List<Skill> skills;

    // NOTE: This will cause circular relations - Infinite loop
    //    @Relations(edges = PersonRelationshipEdge.class, lazy = true)
    //    private List<PersonNode> relations;

    public PersonNode(String username, String description, int age, String imageUrl,
            List<Skill> skills) {
        this.description = description;
        this.username = username;
        this.age = age;
        this.imageUrl = imageUrl;
        this.skills = skills;
        //        this.relations = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", username='" + username + ", description='" + description
                + ", age=" + age + ", imageUrl=" + imageUrl + '}';
    }
}
