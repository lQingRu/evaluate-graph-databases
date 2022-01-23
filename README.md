# Evaluating between Arango and Neo4j Graph Databases
## Purpose
- This repository aims to do a targetted POC on Arango and Neo4j as a Graph data store

## Content [WIP]
Gitlab wiki pages in this repository consists of: 
- [Basic Concepts of Neo4j and Arango](https://gitlab.com/qingru97/graph-databases/-/wikis/Basic-Concepts-of-ArangoDB-and-Neo4j)
- [Data Modelling considerations](https://gitlab.com/qingru97/graph-databases/-/wikis/Data-Modelling)
- [Database related considerations](https://gitlab.com/qingru97/graph-databases/-/wikis/Database-related-Concepts)
- Evaluation of ArangoDB and Neo4j
  - [Technical examples for comparisons](https://gitlab.com/qingru97/graph-databases/-/wikis/Evaluation-of-ArangoDB-and-Neo4J-(Technical-Comparisons))
  - [Non-Technical comparisons](https://gitlab.com/qingru97/graph-databases/-/wikis/Evaluation-of-ArangoDB-and-Neo4J-(Non-Technical-Comparisons))

## Setup
1. Run non-persistent data single node Neo4j and arangoDB on docker:
- username: `root`, password: `password`
```
docker-compose up
```
2. Run application with `bootRun` which `ArangoInitRunner` will first wipe all the data from ArangoDB before populating some initial data into ArangoDB

## Resources
### Arango
**Getting started on Arango**
- https://www.arangodb.com/docs/stable/drivers/spring-data-reference-mapping.html
- https://www.arangodb.com/docs/stable/aql/graphs-traversals.html
- https://www.arangodb.com/tutorials/spring-data/part4-query-methods/
- https://www.arangodb.com/docs/stable/tutorials.html

### Neo4j
**Getting started on Neo4j**
- https://www.youtube.com/watch?v=ou2st6FYxR8&ab_channel=Neo4j
- https://www.youtube.com/watch?v=4C4zqhXwCKs&t=926s&ab_channel=LearnCode.academy
- [Neo4j's data on disk](https://neo4j.com/developer/kb/understanding-data-on-disk/#:~:text=Properties%20are%20stored%20as%20a,its%20start%20and%20end%20node.)

**Issues**
- https://newbedev.com/storing-object-as-property-in-neo4j
- Neo4j doesn't have a `GROUP BY`
    - e.g.: https://stackoverflow.com/questions/32389657/how-to-match-only-one-relationship-between-two-nodes
