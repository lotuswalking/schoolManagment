FROM openjdk:8
ADD target/schoolmanagment.jar schoolmanagment.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar","schoolmanagment.jar"]
