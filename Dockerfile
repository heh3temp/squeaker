FROM gradle:7.3-jdk17

RUN mkdir -p /home/squeaker

COPY . /home/squeaker

RUN /home/squeaker/gradlew build -x test -p /home/squeaker/

CMD ["java", "-jar", "/home/squeaker/build/libs/squeaker-0.0.1-SNAPSHOT.jar"]
