# tb.lx Backend Challenge

Backend coding challenge proposed by tb.lx during interview phase

# About

The goal of this challenge is to build a web service that exposes Vehicle, Operator and Trace data from [Dulin Bus GPS sample data](https://data.gov.ie/dataset/dublin-bus-gps-sample-data-from-dublin-city-council-insight-project), for a given time frame. The service exposes a RESTful API to answer the following questions:

1. Given a time frame [startTime, endTime], what is the list of running operators?

2. Given a time frame [startTime, endTime] and an Operator, what is the list of vehicle IDs?

3. Given a time frame [startTime, endTime] and an Operator, which vehicles are at a stop?

4. Given a time frame [startTime, endTime] and a vehicle ID, return the trace of that vehicle (GPS entries, ordered by timestamp).

# Resources used in this project

* Java
* Spring Boot
* Elasticsearch
* Logstash
* Swagger (for generating documentation)
* Mockito (for tests)

# Getting started

To be apple to run this project, docker and docker-compose are required.\
For more information about how to install docker, please visit https://docs.docker.com/install/\
For more information about how to install docker-compose, please visit https://docs.docker.com/compose/install/

# Running this project

1. Make sure you have [Docker](https://docs.docker.com/install/) and [Docker Compose](https://docs.docker.com/compose/install/) installed
2. Close this repository
3. Run `docker-compose up` to start the service. This may take a few minutes since images for elastichsearch, logstash and linux alpine need to be downloaded. Also, since the data sample used is quite large, it may take a few minutes as well for logstash to fully load it into elastichsearch.
4. Access http://localhost:8080/swagger-ui.html to view the API documentation. You can also test the API at this URL. If you prefer, you can use `traces_api.yaml` to load route definition into your client of choice (Insomnia, Postman, etc)

# Folder structure

The code of the web service is located at `api/`\
The jar generated is located at `api-jar/`. It is there for Docker Compose use.
The folder `data/` contain the data sample used and the logstash file configuration used to load the sample into elasticsearch.

# Routes exposed

More information about the API exposed can be found at http://localhost:8080/swagger-ui.html after running the project

## Get operators by timeframe range

GET `/operators/startTime=2012-11-10&endTime=2012-11-11`

## Get vehicles by timeframe range and operator

GET `/vehicles/startTime=2012-11-10&endTime=2012-11-11&operator=PO`

## Get vehicles by timeframe range, operator and at stop

GET `/vehicles/startTime=2012-11-10&endTime=2012-11-11&operator=PO&atStop=true`

## Get traces by timeframe range and vehicle id

GET `/tarces/startTime=2012-11-10&endTime=2012-11-11&atStop=33438`