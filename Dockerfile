FROM maven


WORKDIR /app


COPY pom.xml /app

RUN mvn clean install

COPY . /app


RUN mvn package


EXPOSE 8080

CMD [ "java","-jar","/app/target/cif-0.0.1-SNAPSHOT.jar" ]





