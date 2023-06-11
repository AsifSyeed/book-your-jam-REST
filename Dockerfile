FROM amazoncorretto:11
COPY --from=build /target/BookYourJam-0.0.1-SNAPSHOT.jar bookyourjam.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "bookyourjam.jar"]