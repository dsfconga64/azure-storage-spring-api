FROM openjdk:8
EXPOSE 8282
ADD target/storage-api-restapi.jar storage-api-restapi.jar
ENTRYPOINT ["java","-jar","/storage-api-restapi.jar"]