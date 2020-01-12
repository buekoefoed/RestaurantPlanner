 ## README
 
 ### CA3 Startcode
 Source: <https://github.com/emilgth/CA3>
 
 ### Initial setup
 - Download and extract project folder
 - Create a new empty repository on <https://github.com>
 - Delete the old .git folder and run a git init and add, commit and push the project to the repository 
   - `git init`
   - `git add .`
   - `git commit -m "initial commit"`
   - `git remote add origin __INSERT URL TO REPOSITORY__`
   - `git push -u origin master`
 - Create __local__ database and test database on vagrant
 - Create __remote__ database and test database on droplet 

 ### Default changes
 - Make sure that the IDE enables _maven_ and _persistence_
 - Delete or refactor _RenameMe_, _FacadeExample_ and _RenameResource_ to reflect the current project
 - Run the class _SetupTestUsers_ to populate the DB
 - Copy the DB to remote droplet
   - SQL scripts -> SQL generator -> Run SQL in droplet terminal to create framework
   - Copy test data as needed
 
 ### Deployment setup
 - Change the default remote server in `pom.xml`
   `<remote.server>https://__INSERT URL TO DOMAIN (example.com)__/manager/text</remote.server>`
 - On remote droplet add environment variables so project can access database
   - `sudo nano /opt/tomcat/bin/setenv.sh`
   - The file should contain the following:
     `export DEPLOYED="DEV_ON_DIGITAL_OCEAN"`
     `export USER="YOUR_DB_USER"`
     `export PW="YOUR_DB_PASSWORD"`
     `export CONNECTION_STR="jdbc:mysql://localhost:3306/NAME_OF_YOUR_DB"`
   - Save the file, and restart Tomcat `sudo systemctl restart tomcat`
 - Open a __local__ terminal in the project folder
   - Deploy the project
     `mvn clean test -Dremote.user=script_user -Dremote.password=PW_FOR_script_user tomcat7:deploy`- 

 ### Travis setup
 - Change script in `travis.yml` (line 43) `CREATE DATABASE startcode_test;` to match the test database name
 - If you followed the deployment step and have a deployed version of the project on droplet.
   - Change script in `travis.yml` (line 15) to:
   `mvn -Dremote.user=$REMOTE_USER -Dremote.password=$REMOTE_PW tomcat7:undeploy tomcat7:deploy`
 - Allow tracking of project on <https://travis-ci.org>
 - Add tomcat environment variables to project on travis so the project can be automatically deployed
   - key: `REMOTE_USER` &ensp; value: `"script_user"`
   - key: `REMOTE_PW` &ensp; value: `"PW_FOR_script_user"`

 ### Frontend setup
 - Clone frontend project from <https://github.com/emilgth/CA3-Front>
 - Install npm
   - `npm install`
 - Change the domains in _settings.js_ to match your own endpoints
 - Build the project
 - Deploy frontend using surge
   - In a terminal run `surge`
   - add `build` to path
   - Choose a name for the domain
