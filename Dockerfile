FROM amazoncorretto:11
COPY target/BookYourJam-0.0.1-SNAPSHOT.jar bookyourjam.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "bookyourjam.jar"]