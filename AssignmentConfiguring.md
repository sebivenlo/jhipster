# The Beverage Chart Assignment

## Generating Entities

- Go to the JDL Studio webpage at http://www.jhipster.tech/jdl-studio/.
- In JDL Studio, create a BeverageChart entity, with a required string variable called "name".
- In JDL Studio, create a Beer entity, with the required string variable called "name", a required float variable     called "price" and a required float variable called "quantity".
- In JDL Studio, create a Property entity, with a required string variable "name" 
- In JDL Studio, create a many to one relationship from Beer to BeverageChart on the name variable of BeverageChart.
    (Optional: Create a Wine entity with similar relationships as Beer)
- Import your entity table: 
    
  - At the top right corner of the screen there should be an icon that shows a down arrow. Click this icon to download a .jh file to your computer. 
  - You have to save this file to the root directory of your project.
    
    - For Docker users:
    
      This is in the root directory of your shared folder. 
      If you have trouble locating this folder, Kitematic shows the folder location under the "VOLUMES" 
      tab of your docker container. 
      Clicking this folder location automatically navigates you to the folder if you have the shared 
      folder configured correctly.
    
      - Then connect to your container in the terminal prompt using:
    ```
        docker container exec -it <container_name> bash
    ```
    
       Tip: It is possible to use the kitematic terminal, 
       which automatically connects to the container.
    
       Import the generated entity table using (Warning: Make sure that the maven and yarn processes 
       are not running. Restarting your container is an easy way to ensure this.)
        
    ```
        jhipster import-jdl your-jdl-file.jh
    ```
    - For manual installation users:
    
    The root directory is where you generated your project when configuring JHipster.
    
    Open a terminal and navigate to the root folder of your JHipster project.
    
    Import the generated entity table using (Warning: Make sure that the maven and yarn processes are not running.)
    ```
    jhipster import-jdl your-jdl-file.jh
    ```
    You may be asked to overwrite some files, type "a" and press enter.
    
- Start your Spring Boot application using ./mvnw in the terminal at the same location as you did the import.

- Start your Webpack development server using yarn start in a separate terminal at the same location as you did the import.
    
- You should now be able to access the application at http://localhost:9000/
    
## Filling the database with some data
  
- Once you have succesfully opened the web application, login into the default admin account using the username "admin", and password "admin".
- Click on the Entities menu, you should see all of the Entities that you imported.
- Navigate to the BeverageChart Entity and press the button in the top right screen to Create a new Beverage Chart, fill it with some data and press Save.
- Navigate to the Property Entity and create a few new properties in a similar way. Properties are linked to beverages, to indicate for example the type of beer.
- Navigate to the Beer Entity and create a few new beers, you can now select the beverage chart and and select multiple properties that you have just created ( with the shift or control button ).
  
## Making changes to the Business Logic
  
```
    Note: It is not possible to provide documentation in the code as all of the classes are generated.
```
```
    Note: After making a change in your class, saving the application should trigger a restart of the
    application thanks to Spring Boot's developer tools. This may not function correctly if using Docker.
```
- Manage access to your entities.

    - By default, all users are allowed to view all beverage charts, all beers and all wines. 
    Instead the user should only be allowed to view his own beverage charts, beers and wines.
      
    - You should have an opened JHipster project in netbeans.
      In your project navigate the *Entity_Name*Resource.java, and think of which REST request to modify.
```      
Tip: Find by current user.
```      
## Making changes to the User Interface

- Make sure that the introduction field of your beverage chart no longer escapes HTML code (TIP: Angular manages this)

- Give the beer and wine lists a stacked layout in a single column (TIP: Stop using a HTML table) 
```
Your browser should be automatically reloaded everytime you save a file
```

