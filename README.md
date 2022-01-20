# Evaluating between Arango and Neo4j Graph Databases

## Areas of Evaluation
### General
- Documentations
- Community
- Features
- Monitoring
- Scalability 
   - Not of concern 

### Requirements-specific
- Extension of graphy cases
  - Traversal
  - Shortest Path
- CRUD 
- Nested structure within each edge
  - Model
  - Store
  - Query

## Setting the stage
### Entities
- Person 
    - `username`: String
    - `age`: int
    - `description`: String
    - `imageUrl`: String
    - `skills`: List of `Skill` objects
- Relationship
    - `source`: Source object
    - `closeness`: String
- Skill
    - `skill`: String
    - `description`: String
    - `strength`: int
    - `type`: String
- Source
    - `type`: String
    - `description`: String
    - `startDate`: LocalDateTime

### Edges
Person relationship
- 1 person can have a 1:M relationship with another person
- Each relationship has the following information:
    - `closeness`: How close is the relationship person A -> person B
    - `source`: How did this relationship come about
    
### Evaluation Use Cases
#### Simple CRUD
Create 
1. Add a person -> OK
2. Add a skill of a person
3. Add a specific relationship of a person

Read
1. Get all direct relationships of a person
~~2. Get more information of a specific relationship~~

Delete
1. Remove a relationship
2. Remove a person and thus all relationships linked to the person

#### Traversals (Arbitrary number of hops over edge collections)
1. Find 2nd tier people and their relationships 
2. Find up to 3 tiers relationships

#### Nested attributes
- Nested attributes here are:
   - `Source` in `Relationship`
   - List of `Skill` in `Person`

## Resources
### Arango
**Getting started on Arango**
- https://www.arangodb.com/docs/stable/drivers/spring-data-reference-mapping.html
- https://www.arangodb.com/docs/stable/aql/graphs-traversals.html
- https://www.arangodb.com/tutorials/spring-data/part4-query-methods/
- https://www.arangodb.com/docs/stable/tutorials.html

**Issues**
- [Self-referential relations caused Stackoverflow](https://issueexplorer.com/issue/arangodb/spring-data/220)
 

### Getting started on Neo4j
- https://www.youtube.com/watch?v=ou2st6FYxR8&ab_channel=Neo4j
- https://www.youtube.com/watch?v=4C4zqhXwCKs&t=926s&ab_channel=LearnCode.academy

**Issues**
- https://newbedev.com/storing-object-as-property-in-neo4j
