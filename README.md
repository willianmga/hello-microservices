# Hello-Microservices

Hello Microservices is a hello world project created in order to practice the principles of microservices architeture.
It allows you to save, find, update and delete people. That's all it does! ;)

It has been develop using the most recent technologies such:
	
	- Java 8 for implementing the software along with some importantant dependencies
		- Google Guice
		- Jersey
		- Netflix Hystrix
		- ReactiveX
		- Hibernate
		- Jackson Mapper
		- Apache HttpClient
		- JUnit
		- Mockito
		- RestAssured

	- MySQL Server for data persistence		
	- Docker for running the services in any environment it may be
	- Apache Maven for dependencies management and build processes
	- Apache Tomcat for web app deployment
	- GuiHub as SCM and issues management software
	- Postman for API testing

## Running With Docker

You can run hellomicroservices by installing docker on your computer and running docker-compose.yml file with docker compose.
	
```sh
$ docker-compose up
```

After running the above command you will have to discover what is the IP Address of midlle container. You can do it by running the following command.


```sh
$ docker ps
```

Then get the container id of hellomicroservicesedge container. 

After you got the container id , then run the following command replacing [EDGE_COTAINER_ID] by
the id returned by [COTAINER ID] column.

```sh
$ docker inspect [EDGE_COTAINER_ID] | grep IP
```

Then get the address of property IPAddress returned by the above command

NOTE: In order to run the following run requests you will have to replace the [EDGE_COTAINER_IP] variable by the ip
given in IPAdress property returned above.

## Requests Examples

### Create Person

This request allows you to create a person

Request
```sh
$ curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{
	"firstName": "Willian",
	"lastName": "Azevedo",
	"email": "willian@azevedo.com",
	"nickName": "willian", 
	"city": "Maringá",
	"state": "Paraná",
	"country": "Brasil",
	"zipCode": "87053000"
}' "http://[EDGE_COTAINER_IP]:8090/hellomicroservicesedge/persons/"
```

Response

```javascript
{
  "personID": "939a8f97-86d8-4657-ab15-f21b25d48000",
  "message": "Success"
}
```

NOTE: save the personID property value so that you can UPDATE, FIND and even DELETE person through his/her id. 

Replace {PERSON_ID} variable in the nexts requests examples by personID property value returned in this request.

### Update Person by ID

Request
```sh
$ curl -X PUT -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{
	"firstName": "Willian",
	"lastName": "Azevedo",
	"email": "willian@azevedo.com",
	"nickName": "willian", 
	"city": "Maringá",
	"state": "Paraná",
	"country": "Brasil",
	"zipCode": "87053000"
}' "http://[EDGE_COTAINER_IP]:8090/hellomicroservicesedge/persons/{PERSON_ID}"
```

Response

```javascript
{
  "personID": "939a8f97-86d8-4657-ab15-f21b25d48000",
  "message": "Success"
}
```

### Delete Person by ID

Request
```sh
$ curl -X DELETE -H "Cache-Control: no-cache" "http://[EDGE_COTAINER_IP]:8090/hellomicroservicesedge/persons/{PERSON_ID}"
```


### Query Person by ID

Request:
```sh
curl -X GET -H "Cache-Control: no-cache" "http://[EDGE_COTAINER_IP]:8090/hellomicroservicesedge/persons/{PERSON_ID}"
```

Response:
```javascript
{
  "uuid": "939a8f97-86d8-4657-ab15-f21b25d48000",
  "firstName": "Willian",
  "lastName": "Azevedo",
  "email": "willian@azevedo.com",
  "nickName": "willian",
  "address": {
    "city": "Maringá",
    "state": "Paraná",
    "country": "Brasil",
    "zipCode": "87053000"
  }
}
```

### Query Person by Query Params

This endpoint provides the possibility to query person first name, last name or even zip code by givin valus to the params firstName, lastName and zipCode.
NOTE: These parameters are optional. If you wish to find all people just dont put them in your request.

Request:
```sh
curl -X GET -H "Cache-Control: no-cache" "http://[EDGE_COTAINER_IP]:8090/hellomicroservicesedge/persons?firstName={PERSON_FIRST_NAME}&lastName={PERSON_LAST_NAME}&zipCode={PERSON_ZIP_CODE}"
```

where {PERSON_ID} is the uuid of person returned in personID property of the below POST response

Response:
```javascript
{
     {
	  "uuid": "939a8f97-86d8-4657-ab15-f21b25d48000",
	  "firstName": "Willian",
	  "lastName": "Azevedo",
	  "email": "willian@azevedo.com",
	  "nickName": "willian",
	  "address": {
	    "city": "Maringá",
	    "state": "Paraná",
	    "country": "Brasil",
	    "zipCode": "87053000"
	  }
      }
}
```
