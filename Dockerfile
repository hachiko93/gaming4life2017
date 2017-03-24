FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/gaming4life2017.jar /gaming4life2017/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/gaming4life2017/app.jar"]
