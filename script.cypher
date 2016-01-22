using periodic commit
LOAD CSV WITH HEADERS
FROM "https://raw.githubusercontent.com/jlavera/ejemplo_neo4j/master/output/all.csv" AS line
MERGE (follower:Account { name: line.follower })
MERGE (followed:Account { name: line.followed })
CREATE (follower)-[:FOLLOWS]->(followed)
return line;
