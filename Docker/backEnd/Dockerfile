#build stage
FROM maven:3.8.7-openjdk-18 as build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

#runtime stage
FROM amazoncorretto:17
#define a argument
ARG PROFILE=dev
ARG APP_VERSION=1.0.0

WORKDIR /app

ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}
ENV DB_URL=jdbc:postgresql://postgresql-dev-bsn:5432/book_network

#the port
EXPOSE 8089

COPY --from=build /build/target/book-network-*.jar /app/

CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL}  book-network-${JAR_VERSION}.jar

#pour faire run a cette file il faut faire
# 1 entre dans le fichier de backend book-network and after that we should run the commande
# 2 docker build -t bns/bns:1.0.0 -f ../Docker/backEnd/Dockerfile .
# apres ca tous va marche

