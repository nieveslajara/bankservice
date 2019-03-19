# Bank Account Service - a simple REST service

## Considerations ## 

*	Solved in the Java recommended framework: Spark.
*	This version just keeps the list in memory, lightweight REST service.
*	The code is based on Maven management, so if running locally Maven tool is required to be installed and configured.
*	A typical RESTful application is expected to receive POST request. Therefore, a POST request for the endpoint /bankaccounts is also included with JSON objects as part of the payload. It also checks if the JSON object is well-formed by Jackson package included in the pom.xml.
*	Lombok is another package imported in the pom.xml that adds getters, setters, equals, etc. repetitive methods by processing annotations.
*	The condition “If there is a bank account with a positive balance that is at least twice as high as all other bank accounts, return the ‘id’ of that account”. The “other bank accounts’ measurement is not very clear, so the following is assumed: among all bank accounts, the two accounts with the highest positive balances with synthetic=false are selected and compared. If one of these two accounts balance is twice  as high as the other’s, then that bank account is returned as the default account. Otherwise, null is returned.
*	The logic for the conditions described is tested using some Unit tests inside the implemented data model.
*	Both local and docker build and executions run first the logic unit tests.

## Running ##

### Locally ###

`` mvn compile && mvn exec:java ``

### Docker container ###
`` docker build -t nieves/bankservice . ``

`` docker run -p 4567:4567 nieves/bankservice `` 

### docker-compose (Recommended) ### 

It may take some seconds.

`` sudo docker-compose build ``

`` sudo docker-compose up ``


## Results ##

* [http://localhost:4567/bankaccounts](http://localhost:4567/bankaccounts).

* [http://localhost:4567/default](http://localhost:4567/default).


## Further comments ##

Challenging, I founded interesting to try for the first time Spark, the recommended Java framework to build the REST service in the problem description. Normally I’m more used to use Spring or NodeJS. In the end I really liked and enjoyed Spark.

In a real robust scenario the data content storage should be persistent. An approach to cover this persistence may be add an ORM (Object-relational mapper) such as Hibernate  with pure SQL approach like Sql2o.
