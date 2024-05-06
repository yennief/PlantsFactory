FROM maven:3.8.6-openjdk-11
EXPOSE 8085

WORKDIR /app
COPY . /app

RUN mvn package

FROM openjdk:11
COPY --from=0 /app/target/*.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]