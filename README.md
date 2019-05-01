
The architecture consist of following services:

INFORMATION-SERVICE	

ITINERARY-API-GATEWAY	

ITINERARY-HYSTRIX-DASHBOARD	

ITINERARY-MANAGEMENT-SERVICE

REGISTRYSERVICE


Git pull

https://github.com/ajithkumar9754/itinerary-repo.git

Build and Run

Install Docker 
Install Maven

cd itinerary-repo-master

mvn clean install -DskipTests && docker-compose up --build

Technology stack used


Java 8 
Springboot  for Microservices 
Spring Security for secured Endpoints - Enabled spring security and used inline memory authentication . refer WebSecurityConfig.java for details

Swagger library for API documentation - Did some customisation on top of default configurations of swagger, Please refer SwaggerConfig.java for details

SpringSlueth for distributed tracing Log will be consisting of TraceI and SpanId to track application logs which might be in different machines

Eureka for service registry and discovery - Centralised registry for all the services. all the services will be  attached to Eureka server as a client

Zulu gateway for API gateway will route all the incoming request to corresponding backend services

Docker for containerisation , create images for each components and used docker compose to manage all the docker containers . refer docker-compose.yml for details

Apache maven-3.6.0 -For build application

MySql database : MySQL  used as the database for the application. Used mysql docker images to configure database

Liquibase library to manage database management, like database and table creation based on the liquibase configuration files. Enabled liquibase for springboot. Please refer liquibase folder in itinerary-management-service for the implementation details.


FeignClient - is used for micro-service to micro-service communication(INFORMATION-SERVICE	 --> ITINERARY-MANAGEMENT-SERVICE service calls)




Once application is UP and running with below command

mvn clean install -DskipTests && docker-compose up --build

Please verify the Eureka server to confirm all the services UP and Running with URL

http://localhost:8761/

Application	Name                PORT 

INFORMATION-SERVICE	            8085

ITINERARY-API-GATEWAY	        9090

ITINERARY-MANAGEMENT-SERVICE	8081


If All the services are UP 

Please use Swagger UI for API reference

We have 2 services 

1 . ITINERARY-MANAGEMENT-SERVICE  ---> http://localhost:8081/swagger-ui.html#/travel-management-controller   --> admin users

2. INFORMATION-SERVICE	          ---> http://localhost:8085/swagger-ui.html#/travel-information-controller  --> travelUser


Please note ITINERARY-MANAGEMENT-SERVICE endpoints are restricted to 'admin' users . so please use the required  token for the same.

From swagger UI we  can able to invoke API's. Please use the proper data to invoke. Please refer the user guide .


<-------Future improvement ( Please note because of time constraint I couldn't able to implement few items below)---->

Spring Client config with docker. Tried to implement it, But having some issues with container to container communication- Disabled the feature in this app

Spring Security with oAuth2- We can integrate spring security with OAuth2. It will give security for applications, with token validity and all

Central logging mechanism for the docker based applications with logback.xml configurations

Regarding the security we can use java vulnerability libraries to scan request and responses.

SSL integration in docker container

Data encryption in the database while saving and decryption while retrieval

Important Note

<--We can use docker-compose or kubernetes scale up feature for application scaling based on the peak loads--->






REST API 's few sample invocations via REST client like postman

Travel Management Controller



POST  http://localhost:9090/api/v1/management/travelinfo   -->  Create Travel Information


Authorization  -- >  Basic YWRtaW46YWRtaW5AUGFzc3dvcmRAMQ==


travelInfoDTO Request Body -->

{
  "arrivalTime": "2019-01-19 04:30",
  "departureTime": "2019-01-19 02:30",
  "destinationCity": "delhi",
  "numberOfConnections": 1,
  "sourceCity": "cochin"
}



GET  http://localhost:9090/api/v1/management/travelinfo/route?sourcecity=cochin&destinationcity=delhi  -->  Get Shortest route between 2 cities


response 


{
  "travelId": 1,
  "sourceCity": "cochin",
  "destinationCity": "delhi",
  "arrivalTime": "2019-01-19 04:30:00.0",
  "departureTime": "2019-01-19 02:30:00.0",
  "numberOfConnections": 1,
  "duration": 2
}



GET /api/v1/management/travelinfo    -->  Get All the Travel Informations between 2 cities


PUT  /api/v1/management/travelinfo  --> Update Travel Information


GET  /api/v1/management/travelinfo/{traveliId   --> Get Travel Information with TravelId


GET   /api/v1/management/travelinfo/route   -->  Get Shortest route between 2 cities

_______________________________________________________________________________________



Travel Information Controller- This will be consume above 'Travel Management Controller ' with user 'traveluser'


GET /api/v1/information/travel  --> Get All the Travel Informations between 2 cities

GET /api/v1/information/travel/route  -->Get Shortest route between 2 cities



GET http://localhost:9090/api/v1/information/travel/route?sourcecity=cochin&destinationcity=delhi

Authorization  -- >  Basic dHJhdmVsdXNlcjp0cmF2ZWx1c2VyQFBhc3N3b3JkQDE=






