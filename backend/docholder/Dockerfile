FROM maven:3.6.3-ibmjava-8-alpine

WORKDIR /backend

COPY . .

RUN mvn install && mvn clean install

EXPOSE 8081

CMD mvn spring-boot:run