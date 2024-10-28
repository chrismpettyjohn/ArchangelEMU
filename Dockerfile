FROM amazoncorretto:19

RUN yum update -y && yum install -y maven

WORKDIR /app

COPY pom.xml ./

RUN mvn dependency:go-offline -B

COPY . .

RUN mvn clean install -DskipTests

CMD ["java", "-jar", "target/Archangel-4.0-DEVPREVIEW-jar-with-dependencies.jar"]
