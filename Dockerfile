FROM openjdk:17
EXPOSE 8080
ADD target/spring-boot-rbc-stock.jar spring-boot-rbc-stock.jar
ENTRYPOINT ["java", "-jar", "/spring-boot-rbc-stock.jar"]
WORKDIR /stock-demo
COPY . ./
