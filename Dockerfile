FROM amazoncorretto:11
COPY target/*.jar BookYourJam-0.0.1-SNAPSHOT.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "bookyourjam.jar"]