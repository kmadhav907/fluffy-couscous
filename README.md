# SpringBootMusic

- Install Eclipse Web Developer from here <a href="https://www.eclipse.org/downloads/">Click here</a>

- Click on File > Import  
![image](https://user-images.githubusercontent.com/54632221/179565571-dcdcce7b-ab32-4b8d-ad2f-06f170e5cbae.png)

- Select Git > Smart Import <br/>
![image](https://user-images.githubusercontent.com/54632221/179565942-12af98e7-0549-423b-ba6a-c39a329461cc.png)

- Enter your Github URI of the repository > Select the Branch > Select the Directory to clone the repository > Click on Finish

![image](https://user-images.githubusercontent.com/54632221/179566577-60176a7b-0b7c-46f1-bc4a-d99c0ad6275a.png)

- Go to src/main/resources/application.properties add your Database Configuration of MySQL

- Go to src/main/java/com/example/music/MusicApplication/MusicApplication.java and run it has Java Application

- Run MySQL.sql file in your MYSQL workbench

- Your Backend Server will run on localhost:8082

- Go to http://localhost:8082/admin to add Category and Product(Music)

- http://localhost:8082/register to register a new user

- http://localhost:8082/login to login 

- localhost:8082/shop to view different Music with description

- If Spring Security asks for User Credentials for the backend server add these lines to src/main/resources/application.properties file
```
spring.security.user.name=user
spring.security.user.password=1234
```

