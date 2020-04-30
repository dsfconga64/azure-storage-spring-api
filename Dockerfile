FROM openjdk:8
EXPOSE 8282
ADD target/storage-api-restApi.jar storage-api-restApi.jar
ENTRYPOINT ["java","-jar","/spring-api-restApi.jar"]