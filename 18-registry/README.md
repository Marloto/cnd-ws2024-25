## Local Registry

```
docker run -d -p 5001:5000 --restart=always --name registry registry:2
docker build -t reg-test .
docker tag reg-test localhost:5001/reg-test:v1.0
docker push localhost:5001/reg-test:v1.0
docker image rm reg-test
docker image rm localhost:5001/reg-test:v1.0
docker run --rm localhost:5001/reg-test:v1.0
```

## Docker Reg

```
docker login registry-1.docker.io
docker tag reg-test registry-1.docker.io/marloto/registry-test:v1.0
docker push registry-1.docker.io/marloto/registry-test:v1.0
docker run --rm registry-1.docker.io/marloto/registry-test:v1.0
```

Domain Alias:

```
docker login 
docker login registry.hub.docker.com
```