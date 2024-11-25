#!/bin/bash
MYSQL_USER=user
MYSQL_PASSWORD=C1CD107B-AA58-498B-9D5A-9DBBF07B379F
MYSQL_ROOT_PASSWORD=2B62661B-D644-4A81-91DA-F0DB0961CDF4
MYSQL_DATABASE=somedb

docker network create --driver bridge app-example

docker run -d --name db --network app-example -e "MYSQL_USER=${MYSQL_USER}" -e "MYSQL_PASSWORD=${MYSQL_PASSWORD}" -e "MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD}" -e "MYSQL_DATABASE=${MYSQL_DATABASE}" mysql:5.7

docker run -d --name broker --network app-example -v `pwd`/mosquitto.conf:/mosquitto/config/mosquitto.conf eclipse-mosquitto

docker build -t app .

docker run -d --name app --network app-example app:latest --spring.jpa.hibernate.ddl-auto=update --spring.datasource.url=jdbc:mysql://db:3306/${MYSQL_DATABASE} --spring.datasource.username=${MYSQL_USER} --spring.datasource.password=${MYSQL_PASSWORD} --spring.datasource.driver-class-name=com.mysql.jdbc.Driver --mqtt.broker=tcp://broker:1883

docker run -d -p 12345:80 --name proxy -v `pwd`/nginx.conf:/etc/nginx/nginx.conf --network app-example nginx:1.21
