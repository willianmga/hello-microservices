# Hello-Microservices

Hello Microservices is a hello world project created in order to practice the principles of microservices architeture.
It allows you to save, find, update and delete person. That's all it does! ;)

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
$ docker-compose -f ./docker-compose.yml up -d
```

## Discovering the IP Address of hellomicroservicesmiddle container

After running the command above docker will start mysql, edge and middle services container in your machine. The request examples given below will 
all be directed to hellomicroservicesedge container. In order to run the requests you will have to discover the IP Address of this container.
You can do it by running the following the below instructions:

#### 1st.
```sh
$ docker ps
```

Then get the container id of hellomicroservicesedge container as described in CONTAINER ID column of the returned table. 

#### 2nd.
Run the following command replacing [EDGE_COTAINER_ID] by the id the hellomicroservicesedge container id.

```sh
$ docker inspect [EDGE_COTAINER_ID] | grep IP
```

Then get the IP Address existent in IPAddress property returned by the above command.


## Requests Examples
####
NOTES: 
	1st. In order to run the following run requests you will have to replace the [EDGE_COTAINER_IP] variable by the IP Address of hellomicroservicesmiddle container.
	2nd. Save the personID property value returned in create person response so that you can UPDATE, FIND and even DELETE person.
Replace {PERSON_ID} variable in the next requests examples by the value of this property.

### Create Person

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

This endpoint provides the possibility to query person first name, last name or even zip code by giving valus to the params firstName, lastName and zipCode.
NOTE: These parameters are optional. If you wish to find all people just dont put them in your request.

Request:
```sh
curl -X GET -H "Cache-Control: no-cache" "http://[EDGE_COTAINER_IP]:8090/hellomicroservicesedge/persons?firstName={PERSON_FIRST_NAME}&lastName={PERSON_LAST_NAME}&zipCode={PERSON_ZIP_CODE}"
```

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

## Practice it!

I've learned these principles by following an example project. This architeture is a very important one. 
So, I recommend you to do the same so you can learn by yourself.

## Want to contribute?

Contributions are always welcome. If you wish to contribute to this example project just send a pull request or create an issue so that we can work together.

### THAT'S ALL FOLKS!
