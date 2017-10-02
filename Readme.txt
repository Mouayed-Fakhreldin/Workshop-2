This project requires having mySQL DBMS installed on the device. The version we had installed is 5.7.19 You can find it on https://dev.mysql.com/downloads/installer/
--------------
MySQL installation process:
Most importantly is to have MySQL Server and MySQL Shell installed.
1) First, click on the downloaded file and accept the license agreement. On the "Choose Setup Type" screen: choose "Developer Default". Ignore the warnings about failed requirements if they do not concern SQL Server and SQL Shell.
2) Keep clicking next and then execute to start installing.
3) After installing, keep clicking next without changing any settings until you get to "Account and Roles"
4) Provide a root password and do not forget it please !!! 
5) Keep clicking next and then execute to apply configuration and then finish and repeat.
6) On "Connect to Server" screen, click check to check the user's password
7) Execute again and finish installation
-----------------------------------------------------
Now for setting up the database: 
1) Copy workshop2.sql and put it in MySQL/MySQL Server 5.7/bin/ where MYSQL is installed. For example: C:\Program Files\MySQL\MySQL Server 5.7/bin 
2) Open Command line as administrator
3) Type cd Link to where MYSQL Server 5.7 bin is located. For example: "cd C:\Program Files\MySQL\MySQL Server 5.7/bin". This will navigate to the bin folder.
4) type "mysql -u root -p" without the quotes and then provide the root password
5) type "create database workshop2;" without the quotes   
6) type "exit" withouth the quotes
7) type "mysql -u root -p workshop2 < workshop2.sql" to import the .sql dump file into the database and then provide the password
**** Now the database is ready.
----------------------------------------------------------------
In order to run the jar file:
1) Open winrar or winzip as administrator (preferably)
2) Open the jar file included in our project folder ("jar.jar")
3) Open model/UserAndPassword.txt using any text editor.
4) Change the first line to "root" withouth the quotes
5) Change the second line to the root's password you provided when installing MySQL
6) Save the file and save the changes you made in the .jar file
7) Go to Command line and navigate to the file's location. For example: "Cd c:\users\user\desktop\workshop2"
8) type "java -jar jar.jar" without the quotes
* Then you should be able to see the program's main menu
-------------------------------
In order to execute the java source files, do the same changes on ./bin/model/UserAndPassword.txt
--------------------
As for running the .java files, I trust that you are capable of doing that on your own. If you have Eclipse, then you can just import the whole project.
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------