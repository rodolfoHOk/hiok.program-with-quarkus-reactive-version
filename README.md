# Program with Quarkus Reactive Version

> Quarkus Study Project by RudolfHiOk

## Technologies

- Java 17
- Quarkus 3.1
- Hibernate Reactive + Panache
- PostgreSQL

## Create a Quarkus Project

- Access: https://code.quarkus.io/

### Extensions

- quarkus-resteasy-reactive
- quarkus-resteasy-reactive-jackson
- quarkus-hibernate-reactive-panache
- quarkus-reactive-pg-client

## Quarkus CLI (https://quarkus.io/guides/cli-tooling)

- Install Quarkus CLI with sdkman: sdk install quarkus
- Run project for development: quarkus dev
- Add extension: quarkus extension add <extension-name>
- Run tests: quarkus test

## Test coverage

- Install jacoco extension with Quarkus CLI: quarkus extension add jacoco
- Run test coverage: ./mvnw verify
- Open: target/jacoco-report/index.html

## Build

### jar file

- build running tests: ./mvnw package
- build without running tests: ./mvnw package -DskipTests

### container image

- install jib extension with Quarkus CLI: quarkus extension add container-image-jib
- add in application.properties: quarkus.container-image.build=true
- build running tests: ./mvnw package
- build without running tests: ./mvnw package -DskipTests
- check container image <user/project-name>: docker image ls

### container image to Kubernetes

- install quarkus-kubernetes extension with Quarkus CLI: quarkus extension add quarkus-kubernetes
- build running tests: ./mvnw package
- build without running tests: ./mvnw package -DskipTests
- check folder target/kubernetes has: kubernetes.yml and kubernetes.json

## Run

### jar file

- java -jar target/quarkus-app/quarkus-run.jar

### container image with Docker

- docker run --name <container-name> -p 8080:8080 -d <container-image-name>

### Kubernetes with minikube

- install minikube: see Util links minikube
- start a k8s cluster: minikube start
- check k8s version: kubectl version
- set cluster internal docker: minikube docker-env / minikube -p minikube docker-env
- add in application.properties: quarkus.kubernetes.image-pull-policy=never
- build without running tests: ./mvnw package -DskipTests
- kubectl apply -f target/kubernetes/kubernetes.json

## Util links

- [Quarkus RestEasy reactive guide](https://quarkus.io/guides/resteasy-reactive)
- [Quarkus Hibernate Reactive + Panache guide](https://quarkus.io/guides/hibernate-reactive-panache)
- [Quarkus all properties](https://quarkus.io/guides/all-config)
- [Quarkus datasource guide](https://quarkus.io/guides/datasource)
- [Quarkus Dev Services](https://quarkus.io/guides/dev-services)
- [Quarkus container image](https://quarkus.io/guides/container-image)
- [Quarkus deploy to Kubernetes](https://quarkus.io/guides/deploying-to-kubernetes)
- [minikube](https://minikube.sigs.k8s.io/docs/start/)
