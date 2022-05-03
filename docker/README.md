# GraphDB Setup

## TigerGraph

1. Start TigerGraph Docker Container
   1. `docker-compose up tigergraph`
2. Login to Container Image
   1. `ssh -p 14022 tigergraph@localhost`
   2. Default password is tigergraph
3. Start TigerGraph
   1. `gadmin start all`