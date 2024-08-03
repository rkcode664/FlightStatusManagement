## Design Documentation

For the design of the project, please refer to the following document:  
[Project Design Document](https://docs.google.com/document/d/1Fl3TI8t5Ys9M18o0vinR8p7RiJ2x0dhO4vAChJ0-E9A/edit?usp=sharing)

## Technologies Used

- **Backend**: Java and Spring Boot
- **Notification Service**: Kafka
- **Database**: PostgreSQL

## Running the Backend

1. **Ensure Kafka is Running**

   Before starting the backend, make sure Kafka is running. Follow these steps to run Kafka on Windows:

    - Open Command Prompt and change the directory to the Kafka folder. Start Zookeeper with the following command:
      ```bash
      .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
      ```

    - Open another Command Prompt window, change the directory to the Kafka folder, and start the Kafka server with the command:
      ```bash
      .\bin\windows\kafka-server-start.bat .\config\server.properties
      ```

2. **Start the Backend**

   You can start the backend application by running the `FlightApplication.java` class directly from your IDE.
