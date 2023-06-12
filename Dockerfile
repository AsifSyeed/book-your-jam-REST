FROM amazoncorretto:11
COPY . /app
WORKDIR /app
RUN ./mvnw clean package
#COPY /app/target/BookYourJam-0.0.1-SNAPSHOT.jar bookyourjam.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/target/BookYourJam-0.0.1-SNAPSHOT.jar"]
#ENTRYPOINT ["/bin/sh"]