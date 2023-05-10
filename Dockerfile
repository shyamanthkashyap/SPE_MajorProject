FROM openjdk:11
COPY ./target/project-0.0.1-SNAPSHOT.jar ./
WORKDIR ./
CMD ["java", "-jar", "project-0.0.1-SNAPSHOT.jar"]
