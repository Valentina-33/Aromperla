# Etapa 1: compilar con Maven
FROM maven:3.9-amazoncorretto-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: ejecutar solo el jar
FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV PORT=35000
EXPOSE 35000
ENTRYPOINT ["java", "-jar", "app.jar"]