# phabcart

An online medicine delivery system, that is developed using Groovy on Grails 4 as backend Java framework, frontend with Angular 8 and database being MongoDb.

1. Pre-requisites

If you have the pre-requisites already installed, skip the part which is already available

a. Java
    Minimum version of 8 is required to proceed further
    https://docs.oracle.com/javase/8/docs/technotes/guides/install/windows_jdk_install.html#CHDBJHIC

b. Maven

    Follow the installation guide here
    https://maven.apache.org/install.html

c. Grails 4

    Follow the installation guide here
    https://docs.grails.org/latest/guide/gettingStarted.html#requirements

d. Groovy

    Follow the instruction guide here

    http://groovy-lang.org/install.html#_download

e. MongoDb

    Follow the installation guide here
    https://docs.mongodb.com/manual/installation/

f. Angular

   Follow the guide here
   https://medium.com/@prasad.k.pawar/install-angular-8-and-set-up-the-environment-2ee2e703ee19


2. Import the DB scripts available in \System Path\phabcart\readme\DB\importScripts.txt

3. Install client-side dependencies
	cd client
	npm install

4. Running the application

Method 1:

 Run the client seperately from the project folder

	gradlew client:bootRun

	and 

 Run the server seperately from the project folder
	gradlew server:bootRun

Method 2:

 Run the server and client parallely from the project folder

	gradlew bootRun --parallel	

Method 3:

 Run the server seperately from the project folder

step 1:

	cd server
	grails run-app

step 2:
	cd client
	ng serve

The application will run on http://localhost:4200/index, open the link in chrome browser
