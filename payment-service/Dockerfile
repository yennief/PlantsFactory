FROM maven:3.8.6-openjdk-11

WORKDIR /app
COPY . /app

RUN mvn compile
ENTRYPOINT ["mvn", "camel:run"]
