FROM maven:3.9.9-eclipse-temurin-23-alpine

RUN apk add --update maven &&\
    apk add --update sudo

RUN head -c 5 /dev/random > random_bytes && chmod 0777 /etc/passwd

WORKDIR /project

#run.sh the spring boot application
ENTRYPOINT ["mvn", "spring-boot:run"]