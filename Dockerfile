# Use Amazon Corretto 19 as the base image
FROM amazoncorretto:19

# Install git and Maven
RUN yum update -y && yum install -y git maven

# Set the working directory
WORKDIR /app

# Set up the GIT_TOKEN build argument
ARG GIT_TOKEN

# Clone the repository using the GIT_TOKEN
RUN git clone https://${GIT_TOKEN}@github.com/habrpg-com/Archangel-EMU.git .

# Build the application with Maven
RUN mvn clean install -DskipTests

# Run the jar file
CMD ["java", "-jar", "target/Archangel-4.0-DEVPREVIEW-jar-with-dependencies.jar"]

