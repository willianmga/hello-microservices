#!/bin/sh

mvn clean install -DskipTests -o

sudo docker build -t hellomicroservicesmiddle:latest hello-microservices-middle/
sudo docker build -t hellomicroservicesedge:latest hello-microservices-edge/
