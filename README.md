# JHipster
In this workshop you will learn how to install, configure and run a JHipster application.
The first part of the workshop will be theory. The second part has assignments about setting up a JHipster environment and configuring a JHipster application.

Workshop by Joey de Vlieger and Niels Killaars

## Setting up the environment using Docker

### For Linux users

- Linux supports Docker out-of-box. You just need to follow the tutorial on the [Docker](https://docs.docker.com/engine/installation/) website.
- On Linux, you might need to run the docker command as root user if your user is not part of docker group. It’s a good idea to add your user to docker group so that you can run docker commands as a non-root user.
- Pull the latest JHipster Docker image:
```
docker image pull jhipster/jhipster
```
- Create a “jhipster” folder in your home directory:
```
mkdir ~/jhipster
```
- Run the Docker image, with the following options:
  - The Docker “/home/jhipster/app” folder is shared to the local “~/jhipster” folder
  - Forward all ports exposed by Docker (8080 for the Java application, 9000 for BrowserSync, 3001 for the BrowserSync UI)
```
docker container run --name jhipster -v ~/jhipster:/home/jhipster/app -v ~/.m2:/home/jhipster/.m2 -p 8080:8080 -p 9000:9000 -p 3001:3001 -d -t jhipster/jhipster
```
- To check that your container is running, use the command 
```
docker container ps
```
- The easiest way to log into the running container is by executing following command:
```
docker container exec -it <container_name> bash
```
- You can then go to the /home/jhipster/app directory in your container, and start building your app inside Docker:
```
cd /home/jhipster/app
jhipster
```
- JHipster will prompt you with questions about the application that it has to generate. 
The most important options include the following:
  - The type of the application has to be Monolithic
  - Use the JHipster registry (will be used later for microservices)
  - Use SQL type databases
  - For production database use MySQL, for development database use H2 with disk-based persistence
  - Use ehcache
  - Use maven
  - Use Angular 4
  - Use LibSass

- Once your application is created, you can run it using:
```
./mvnw
```
- When doing UI development on a JHipster-generated application, it’s nice to see your changes as soon as you save a file. JHipster 4 uses Browsersync and webpack to power this feature. You enable this by opening a new terminal and connect to the container again:
```
docker container exec -it <container_name> bash
```
- Then execute the following command:
```
yarn start
```
- Please open your generated project from the local shared folder in Netbeans
- Continue the workshop by starting to [configure your JHipster application](https://github.com/sebivenlo/jhipster/blob/master/AssignmentConfiguring.md)


### For Windows and Mac users

- Install the [Docker Toolbox](https://www.docker.com/products/docker-toolbox) to get Docker installed easily.
- Open Kitematic from the docker taskbar icon
- Click on +NEW to create a new container
- Search for JHipster and click on create
- Select your JHipster container in the list and make sure it is NOT running
- In the "Volumes" section click the /home/jhipster/app directory
- In the prompt select "enable volumes"
- Docker might ask if you want to share your drive, select yes
- In Kitematic, select the JHipster container and go to Settings -> Hostname/Ports
- Change all published IP ports to their corresponding docker port (3001, 8080, 9000) and press save
- Start the JHipster container
- Open a new terminal window and execute the following command to connect to the container:
```
docker container exec -it <container_name> bash
```
- Another option is to open a powershell directly from the container in Kitematic
- Start generating a new JHipster application by executing the following command in this terminal:
```
yo jhipster
```
- JHipster will prompt you with questions about the application that it has to generate. 
The most important options include the following:
  - The type of the application has to be Monolithic
  - Use the JHipster registry (will be used later for microservices)
  - Use SQL type databases
  - For production database use MySQL, for development database use H2 with disk-based persistence
  - Use ehcache
  - Use maven
  - Use Angular 4
  - Use LibSass

- When JHipster finishes generating the application it still needs to be started.
Execute the following command in the same terminal to start the back-end:
```
./mvnw
```
- When doing UI development on a JHipster-generated application, it’s nice to see your changes as soon as you save a file. JHipster 4 uses Browsersync and webpack to power this feature. You enable this by opening a new terminal and connect to the container again:
```
docker container exec -it <container_name> bash
```
- Then execute the following command:
```
yarn start
```
- Another option is to open a powershell directly from the container in Kitematic and execute yarn start
- In Kitematic, select the JHipster container and go to Settings -> Hostname/Ports
- Find the published IP port that belongs to docker port 9000 in the list and connect to your application using
```
localhost:publishedIPport
```
- Please open your generated project from the local shared folder in Netbeans
- Continue the workshop by starting to [configure your JHipster application](https://github.com/sebivenlo/jhipster/blob/master/AssignmentConfiguring.md)


## Setting up the environment manually for Windows, Mac and Linux (Without docker)

- Install Java 8 or higher from Oracle
- Install [Git](https://git-scm.com)
- Install [Node.js](http://nodejs.org). Have atleast version 6.9.1
- Install Yarn using the [Yarn installation instructions](https://yarnpkg.com/en/docs/install)
- Run the following command to install Yeoman
```
yarn global add yo
```
- Run the following command to install JHipster
```
yarn global add generator-jhipster
```
- To create a project, open a terminal window and create a directory. For example, mdkdir JHipsterApp. Navigate into the directory and run the command:
```
yo jhipster 
```
- JHipster will prompt you with questions about the application that it has to generate. 
The most important options include the following:
  - The type of the application has to be Monolithic
  - Use the JHipster registry (will be used later for microservices)
  - Use SQL type databases
  - For production database use MySQL, for development database use H2 with disk-based persistence
  - Use ehcache
  - Use maven
  - Use Angular 4
  - Use LibSass

- When JHipster finishes generating the application it still needs to be started.
Execute the following command in the same terminal to start the back-end:
```
./mvnw
```
- When doing UI development on a JHipster-generated application, it’s nice to see your changes as soon as you save a file. JHipster 4 uses Browsersync and webpack to power this feature. You enable this by executing the following command:
```
yarn start
```
- Please open your generated project in Netbeans
- Continue the workshop by starting to [configure your JHipster application](https://github.com/sebivenlo/jhipster/blob/master/AssignmentConfiguring.MD)

## If you do not get a working application running..

- Make sure you follow the manual installation instructions atleast until generating the application
- Clone the StartingApp project from the github repository
- Please open your project in Netbeans
- Continue the workshop by starting to [configure your JHipster application](https://github.com/sebivenlo/jhipster/blob/master/AssignmentConfiguring.md)
