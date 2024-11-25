#!/bin/sh
echo Building thi/hello-world:build
docker build -t thi/hello-world:build . -f Dockerfile.build
docker container create --name extract thi/hello-world:build  
docker container cp extract:/app/build/cmake_hello ./app 
docker container rm -f extract
 
echo Building thi/hello-world:latest
docker build --no-cache -t thi/hello-world:latest .