# Build stage
FROM gradle:7.3-jdk17 as build

WORKDIR /home/squeaker
COPY . /home/squeaker

RUN gradle build -x test

# Run stage
FROM openjdk:17-alpine

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
# RUN chown -R appuser:appgroup /home/squeaker
USER appuser
WORKDIR /home/appuser

COPY --from=build /home/squeaker/build/libs/squeaker-0.0.1-SNAPSHOT.jar squeaker.jar

CMD java -jar squeaker.jar
