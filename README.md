# Taller-05-AREP 
#### Create a CRUD System to Manage Properties
## Property Management System 🏠

This project is a **CRUD (Create, Read, Update, Delete)** system designed to manage real estate properties. It allows users to perform the following operations on property listings:

- **Create** new property listings.
- **Read** or view a list of all properties and individual property details.
- **Update** existing property details.
- **Delete** property listings.

The system is built using a **Spring Boot** backend, a **MySQL** database, and a simple **HTML + JavaScript** frontend. It is deployed on **AWS** using two EC2 instances: one for the backend and another for the database.


https://github.com/user-attachments/assets/475fd1f9-8dc1-47a9-aac1-eb44c1bb8a2b


---
## System Architecture 👩🏻‍💻
The system is divided into three main components:

1. **Frontend**:
    - Built with **HTML**, **CSS**, and **JavaScript**.
    - Communicates with the backend using the **Fetch API**.
    - Provides a user interface for managing properties.
2. **Backend**:
    - Built with **Spring Boot**.
    - Exposes RESTful endpoints for CRUD operations. It uses port 8080.
    - Handles business logic and communicates with the database.
3. **Database**:
    - Built with **MySQL**.
    - Stores property data (ID, address, price, size, description).
    - Hosted in a Docker container on an EC2 instance. It uses port 3306.
      
![codeviz-diagram-2025-03-04T15-10-52 drawio](https://github.com/user-attachments/assets/a2961c95-d94b-4563-905f-8eef129ea356)

Although this is the architecture, the web application (Frontend and Backend) runs inside a Docker container on an EC2 instance, not separately.

![arquitectura](https://github.com/user-attachments/assets/c4dd1654-1332-4e22-b147-0a3ed5b090ef)


### **Interaction Between Components**

- The **frontend** sends HTTP requests (GET, POST, PUT, DELETE) to the **backend**.
- The **backend** processes these requests, interacts with the **database**, and returns the appropriate responses.
- The **database** stores and retrieves property data.

## **Class Design** ✍🏻

The main classes in the backend are:

1. **`Property`**:
    - Represents a property entity.
    - Attributes: `id`, `address`, `price`, `size`, `description`.
2. **`PropertyRepository`**:
    - Interface for database operations.
    - Extends `JpaRepository` to provide CRUD methods.
3. **`PropertyService`**:
    - Contains business logic for property management.
    - Methods: `getAllProperties`, `getPropertyById`, `createProperty`, `updateProperty`, `deleteProperty`.
4. **`PropertyController`**:
    - Exposes RESTful endpoints.
    - Handles HTTP requests and delegates operations to `PropertyService`.



    ```
    src/
      main/
        java/
          arep/
            taller5/
                propertymanagement/               
                    Property.java              # Data model for properties
                    PropertyController.java    # Controller to handle properties
                    PropertyRepository.java    # Repository
                    PropertyService.java       # Service containing business logic
                MicroSpringBoot.java           # Main class
        resources/                    # Static files folder
            static/    
                index.html              # HTML file
                styles.css              # CSS file
                app.js                  # JavaScript file
            application.properties      # Configuration and database connection
      test/
        java/                       # Unit tests
    Dockerfile                      # Docker file
    pom.xml                         # Maven configuration file
    README.md                       # Project documentation

    ```

    ![image](https://github.com/user-attachments/assets/3526a41b-901c-48b0-880c-b698acf70a6d)

## **Installing** ⚙️

1. Download the repository from GitHub in a .zip or clone it to your local machine using Git.
    
    ```
    git clone https://github.com/AnaDuranB/Taller-05-AREP.git
    
    ```
    
2. Navigate to the project directory.
    
    ```
    cd Taller-05-AREP
    ```
    
3. Build the project:
    
    `mvn clean install`
   
## **Deployment Instructions** 📝

Follow these steps to deploy the system on AWS:

### **1. Set Up the MySQL Database**

1. Launch an EC2 instance for MySQL.
2. Connect to the instance via SSH:
    
    ``` bash
    ssh -i "your-key.pem" ec2-user@<public-ip>
    ```
    
3. Install Docker and run a MySQL container:
    
    ``` bash
    sudo yum update -y
    sudo yum install docker -y
    sudo service docker start
    sudo usermod -a -G docker ec2-user
    docker run --name database-container -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=property_db -p 3307:3306 -d mysql:latest
    ```
    
4. Verify the database is running:
    
    ``` bash
    docker exec -it database-container mysql -u root -p
    ```
    ![image](https://github.com/user-attachments/assets/59811d4e-355e-4324-a309-fd5bbaa81873)

5. Verify the database is created:

   ![image](https://github.com/user-attachments/assets/9e7493a9-b3e7-48b8-9fa6-580e7550382a)

6. Test the connection:

  ![image](https://github.com/user-attachments/assets/5b62c7bc-3f1b-4e72-8fe6-5e1512714b99)

7. Testing the web application:

  ![image](https://github.com/user-attachments/assets/ac0de4a9-51a0-4757-9dd9-243f829d67ef)

### **2. Set Up the Spring Boot Backend**

#### Local:

1. Package the application
   
    ``` bash
    mvn clean package
    ```
2. Create the Dockerfile
3. Build the image:

    ``` bash
    docker build -t property-app .
    ```
4. Tag the Docker image
   
    ``` bash
    docker tag property-app <your-dockerhub-username>/property-app
    ```
5. Upload the image to Docker Hub:

    ``` bash
    docker push <your-dockerhub-username>/property-app
    ```
    
#### AWS:
1. Launch another EC2 instance for the backend.
2. Connect to the instance via SSH:
    
    ``` bash
    ssh -i "your-key.pem" ec2-user@<public-ip>
    ```
    
3. Install Docker and pull the backend image:
    
    ``` bash
    sudo yum update -y
    sudo yum install docker -y
    sudo service docker start
    sudo usermod -a -G docker ec2-user
    docker pull <your-dockerhub-username>/property-app
    ```
    
4. Run the backend container:
    
    ``` bash
    docker run -d -p 8080:8080 --name property-app <your-dockerhub-username>/property-app
    ```

### **3. Configure Security Groups**

- For the MySQL instance:
    - Allow inbound traffic on port **3306** from the backend instance.

      ![image](https://github.com/user-attachments/assets/cb28b5de-82b5-432f-b98b-936181c0ae43)

- For the backend instance:
    - Allow inbound traffic on port **8080** from your IP address.
      
      ![image](https://github.com/user-attachments/assets/e4978105-5136-4936-9e65-1b05cb9ec050)

      
## SCREENSHOOTS: 📷
#### Final work:
- GET
![image](https://github.com/user-attachments/assets/7c4ab991-37a9-4bd1-96be-996ffcf2fd6f)

- POST
![image](https://github.com/user-attachments/assets/5f1f8f02-0e36-4c42-9469-a1ec36131f5f)

- PUT
![image](https://github.com/user-attachments/assets/24a1e030-74e9-4cdb-b034-f49c5319b43b)

- DELETE
![image](https://github.com/user-attachments/assets/c0b16c17-1f12-478e-99f6-ad9b04b0b8dd)
![image](https://github.com/user-attachments/assets/b56beeba-3225-48c9-ae58-e5cc7c208b2d)

---
## Running the tests ✅

To run the automated tests:

```
mvn test
```
![image](https://github.com/user-attachments/assets/0ca2ad17-b12e-42a4-968c-c3a6d9ea939d)

These tests verify the server's correct response to different requests.

![image](https://github.com/user-attachments/assets/403a3d45-de56-4888-b873-7eb68cee1515)


## Built With

- [Java SE](https://www.oracle.com/java/) - Programming language
- [Maven](https://maven.apache.org/) - Dependency management and build tool


## Authors

- Ana Maria Duran - *AREP* *Taller 5* - [AnaDuranB](https://github.com/AnaDuranB)




