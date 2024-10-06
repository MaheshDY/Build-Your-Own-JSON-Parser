FROM openjdk:19-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Java source file and test files into the container
COPY . /app

# Compile the Java file, and ensure the class is placed in the correct directory
RUN javac -d . src/SimpleJsonParser.java

# Copy the run-tests script
COPY run-tests.sh /app/run-tests.sh

# Make the script executable
RUN chmod +x /app/run-tests.sh

# Run the test script
CMD ["/app/run-tests.sh"]
