FROM openjdk:17

WORKDIR /usr/src/cimss/

COPY cross-instant-messaging-software-service/target/cross-instant-messaging-software-service.jar /usr/src/cimss/

CMD ["java","-jar","cross-instant-messaging-software-service.jar"]
