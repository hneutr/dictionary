# Project Group 37 GitHub page

The Multifaceted Dictionary application.

## Runtime Environment

What's needed to run the application.

* Database: MySQL 5.7.20 (must be installed on user's system for app to use database)(dictionary DB needs to be setup correctly, automatically, somehow.)
* DB Connector: MySQL Connector 5.1.2.1 (distributed with jar)
* ORM: Hibernate 4.3.6 (distributed with jar)
* Java: version 1.8 (must be installed on user's system)

## Dev Environment 

What's needed to develope the application.

### The IDE
We will be using [Eclipse Java EE IDE for Web Developers](https://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/neon3). We are not building a webapp, this version of the IDE just worked decently for me. We are using Eclipse becuase the vast majority of tutorials are done in Eclipse and it's not too different from NetBeans. Exact Elcipse Version: Neon.3 Release (4.6.3).

### The Build Process
We will be using Maven (1.3.1) to handle our build process and dependencies. Maven will come with the above IDE. Maven will automatically download Hibernate and the DB Connector the first time you build our application. Easiest way to build our application is from the IDE, but there is probably a way to do it from the commandline.

### The Database
To develop on this project you must have [MySQL 5.7.20](https://dev.mysql.com/downloads/mysql/) installed on your system. During installation, it will give you a temporary password for the root account, don't forget what it is! After installing it, a new MySQL icon should appear in your System Preferences. Click the icon and then turn on your database. 

Next, you need the dictionary database setup. For this, see the database creation script. For managing the database directly, use [MySQL Workbench 6.3.9](https://dev.mysql.com/downloads/workbench/). You can execute the database creation script in Workbench. I'll think about how to automate the creation of the database.

Note: When you first try to connect to the MySQL DB from Workbench it will ask for that root password. Once it accepts the password, it will ask you for a new password for root. The password I'm using is "root". I'll think about how to do the DB authentication correctly, for now we are just using root.

### Spring
I'm not sure if we are going to use Spring or not. We aren't making a webapp, so we don't need it. But it's dependency-injection stuff is really nice. 
