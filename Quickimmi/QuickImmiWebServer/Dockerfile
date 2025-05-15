# build
FROM gradle:7.1.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle
RUN gradle build --no-daemon --stacktrace

# packing
FROM amazoncorretto:11-alpine3.19

WORKDIR /opt/app
COPY --from=build /home/gradle/build/libs/*.jar /opt/app/app.jar

ENTRYPOINT ["java", "-jar","/opt/app/app.jar"]

EXPOSE 80
