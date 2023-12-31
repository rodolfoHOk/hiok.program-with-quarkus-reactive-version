# Program with Quarkus Reactive Version

> Quarkus Study Project by RudolfHiOk

## Technologies

- Java 17
- Quarkus 3.1
- Hibernate Reactive + Panache
- PostgreSQL (Reactive driver)
- Tests (Junit5 / RestAssured / Vertx Testing)
- Docker
- OpenAPI (Swagger UI)
- OpenID Connect (OIDC) bearer authentication

## Create a Quarkus Project

- Access: https://code.quarkus.io/

### Extensions

- quarkus-resteasy-reactive
- quarkus-resteasy-reactive-jackson
- quarkus-hibernate-reactive-panache
- quarkus-reactive-pg-client
- quarkus-jacoco
- quarkus-test-vertx
- quarkus-container-image-jib
- quarkus-kubernetes
- quarkus-hibernate-validator
- quarkus-smallrye-openapi
- quarkus-oidc
- quarkus-test-security

## Quarkus CLI (https://quarkus.io/guides/cli-tooling)

- Install Quarkus CLI with sdkman: sdk install quarkus
- Run project for development: quarkus dev
- Add extension: quarkus extension add [extension-name]
- Run tests: quarkus test
- Update version: quarkus update

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
- check container image [user/project-name]: docker image ls

### container image to Kubernetes

- install quarkus-kubernetes extension with Quarkus CLI: quarkus extension add quarkus-kubernetes
- build running tests: ./mvnw package
- build without running tests: ./mvnw package -DskipTests
- check folder target/kubernetes has: kubernetes.yml and kubernetes.json

## Run

### jar file

- java -jar target/quarkus-app/quarkus-run.jar

### container image with Docker

- docker run --name [container-name] -p 8080:8080 -d [container-image-name]

### Kubernetes with minikube

- install minikube: see Util links minikube
- start a k8s cluster: minikube start
- check k8s version: kubectl version
- set cluster internal docker: minikube docker-env / minikube -p minikube docker-env
- add in application.properties: quarkus.kubernetes.image-pull-policy=never
- build without running tests: ./mvnw package -DskipTests
- kubectl apply -f target/kubernetes/kubernetes.json

## OpenAPI

- Access: http://localhost:8080/q/swagger-ui/

## OIDC Test

- Copy quarkus-realm.json from test/config/ to target/classes/
- Run command: quarkus dev
- Access DEV UI: http://localhost:8080/q/dev-v1/
- Click OpenID Connect / Provider
- Click Log into Single Page Application
- username: alice / password: alice

## Util links

- [Quarkus RestEasy reactive guide](https://quarkus.io/guides/resteasy-reactive)
- [Quarkus getting started with reactive](https://quarkus.io/guides/getting-started-reactive)
- [Quarkus Hibernate Reactive + Panache guide](https://quarkus.io/guides/hibernate-reactive-panache)
- [Quarkus all properties](https://quarkus.io/guides/all-config)
- [Quarkus datasource guide](https://quarkus.io/guides/datasource)
- [Quarkus Dev Services](https://quarkus.io/guides/dev-services)
- [Quarkus container image](https://quarkus.io/guides/container-image)
- [Quarkus deploy to Kubernetes](https://quarkus.io/guides/deploying-to-kubernetes)
- [Quarkus validation](https://quarkus.io/guides/validation)
- [minikube](https://minikube.sigs.k8s.io/docs/start/)
- [Quarkus OIDC bearer authentication](https://quarkus.io/guides/security-oidc-bearer-token-authentication-tutorial)
- [Quarkus Security testing](https://quarkus.io/guides/security-testing)
