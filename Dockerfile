FROM amazoncorretto:11
COPY target/*.jar BookYourJam.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "bookyourjam.jar"]