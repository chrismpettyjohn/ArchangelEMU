# Use Amazon Corretto 19 as the base image
FROM amazoncorretto:19

# Install Maven
RUN yum update -y && yum install -y maven git

# Set the working directory
WORKDIR /app

# Clone the repository and build the application
ARG GIT_TOKEN
RUN git clone https://${GIT_TOKEN}@github.com/habrpg-com/Archangel-EMU.git .

# Build the application
RUN mvn clean install -DskipTests

# Run the jar file
CMD ["java", "-jar", "target/Archangel-4.0-DEVPREVIEW-jar-with-dependencies.jar"]
