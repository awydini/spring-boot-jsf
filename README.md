# Cif Application

simple project to illustrate "customer information file" web application, using  spring boot, spring security, Hibernate, prime faces and h2 db.

##  Project requirements

 - maven 3.5 +
 - Jdk 11
 - spring boot 2.5.5
 - prime face 6.2
 - h2 db
 - junit 5
 - mockito

## How to build

    mvn clean install

## How to run 

	mvn spring-boot:run

## Execute tests

	mvn test

## Login details

 - [Login page](http://localhost:8585/login)
 - username : admin
 - password : 123

## h2db console

 - [Login page](http://localhost:8585/h2db)
 - JDBC URL : jdbc:h2:./cif
 - username : aydin
 - password : aydin
