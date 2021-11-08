#!/bin/bash

sudo docker rm -f guochat
sudo docker rmi -f guochatdocker
sudo rm guochat-api.jar
cd guochat-api
git pull
sudo ./gradlew assemble
sudo mv build/libs/guochat-api-0.0.1.jar ~/docker/java/guochat-api.jar
cd ../
sudo docker build -t guochatdocker .
sudo docker run --name=guochat -d -p 8080:8080 -v /var/www/html/file:/file guochatdocker