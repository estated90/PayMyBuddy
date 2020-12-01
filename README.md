# PayMyBuddy

Pay my Buddy is an application that allow people to transfer money from their account to friend's account, without the use of bank account.

It is the 6th project of the OpenClassroom class about java.

## Class Diagram

![Diagramme de classe](https://github.com/estated90/PayMyBuddy/blob/main/ressources-global/pictures/Diagramme%20de%20classe.jpg)

## Database design

![Database design](https://github.com/estated90/PayMyBuddy/blob/main/ressources-global/pictures/Database%20design.png)

For all the detail of the DB, please consult:
https://github.com/estated90/PayMyBuddy/blob/main/ressources-global/DB%20model/index.html

## Getting Started

How to start with the project

### Prerequisites

- Java 1.8
- Maven 3.6.3
- Postgre 13.1

### 

### Installing

1. Install Java:
   https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html
2. Install Maven:
   https://maven.apache.org/install.html
3. Install Postgre:
   https://www.enterprisedb.com/downloads/postgres-postgresql-downloads

### Setup
Create two database :
paydb
paydbtest

Scrtipts are located in ressources / Scripts
P6_01_script db.sql for db of prod
P6_01_script dbtest.sql for db of tests
P6_01_script.sql to create the tables in prod


### Running App

After installing Postgre, Java and Maven, you can find in ressources the script to create the DB. The application run on hibernate, at each start it will create-drop the existing DB.

You can start the application using the IDE of your choice and execute the application through ApplicationPäyMyBuddy.java.

### Testing

To execute tests through maven, go to the root file with a console and type :

mvn test

### Execute jar
To execute the jar of the application. Go to the root of the project and with the commande type :
java -jar target\paymybuddy-0.0.1-SNAPSHOT.jar
