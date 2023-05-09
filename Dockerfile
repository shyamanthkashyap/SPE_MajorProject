FROM openjdk:11
COPY ./target/project-1.0-SNAPSHOT-jar-with-dependencies.jar ./
WORKDIR ./
CMD ["java", "-cp", "calculator-1.0-SNAPSHOT-jar-with-dependencies.jar", "org.example.project"]