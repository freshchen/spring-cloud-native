FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER darcy
VOLUME /tmp
ADD order-1.0.0-SNAPSHOT.jar app.jar
RUN touch /app.jar
ENV JAVA_OPTS=""
EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar --spring.profiles.active=cloud
