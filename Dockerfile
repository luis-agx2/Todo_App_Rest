FROM openjdk:17-jdk-alpine AS init
WORKDIR /app

COPY ./pom.xml /app
COPY ./.mvn ./.mvn
COPY ./mvnw ./

FROM openjdk:17-jdk-alpine AS builder
WORKDIR /app

COPY --from=init app/. .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine AS executor
WORKDIR /app

COPY --from=builder app/target/TodoApp-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "TodoApp-0.0.1-SNAPSHOT.jar"]