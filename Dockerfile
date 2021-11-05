FROM maven


WORKDIR /app


COPY . /app


RUN mvn clean install -DskipTests=true

EXPOSE 8080

CMD [ "java","-jar","/app/target/cif-0.0.1-SNAPSHOT.jar" ]





