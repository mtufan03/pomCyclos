FROM openjdk:8u191-jre-alpine3.8

RUN apk add curl jq

WORKDIR /usr/pomCyclos

ADD target/pomCyclos.jar pomCyclos.jar
ADD target/pomCyclos-tests.jar pomCyclos-tests.jar
ADD target/libs libs

ADD login-module.xml login-module.xml
ADD payment-module.xml payment-module.xml

ADD healthcheck.sh healthcheck.sh

ENTRYPOINT sh healthcheck.sh
