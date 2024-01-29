FROM maven:3.9.2-eclipse-temurin-17-alpine AS build
COPY totem-food-customer-backend /usr/src/app/totem-food-customer-backend
COPY totem-food-customer-application /usr/src/app/totem-food-customer-application
COPY totem-food-customer-domain /usr/src/app/totem-food-customer-domain
COPY totem-food-customer-framework /usr/src/app/totem-food-customer-framework
COPY pom.xml /usr/src/app/pom.xml
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:17.0.2-slim-buster
LABEL maintainer="Totem Food Customer Service"
WORKDIR /opt/app
COPY --from=build /usr/src/app/totem-food-customer-backend/target/*.jar totem-food-customer-service.jar
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:8787", "-jar","/opt/app/totem-food-customer-service.jar"]