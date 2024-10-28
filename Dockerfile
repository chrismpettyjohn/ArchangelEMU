FROM amazoncorretto:19

RUN yum update -y && yum install -y git maven

WORKDIR /app

ARG GIT_TOKEN

RUN git clone https://${GIT_TOKEN}@github.com/habrpg-com/Archangel-EMU.git .

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY . .

RUN mvn clean install -DskipTests

CMD ["java", "-jar", "target/Archangel-4.0-DEVPREVIEW-jar-with-dependencies.jar"]
