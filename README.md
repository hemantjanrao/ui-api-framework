# Automation Suite

## Summary

Framework is build using the stack
* Selenium Webdriver
* Java
* TestNG
* Allure Reporting
* Listeners to take snapshot in case of test failure


<br>

### Table of Contents

* **[Summary](#Summary)**<br>
* **[Setting Developement Environment](#Setting-up-Developement-Environment)**<br>
* **[Running the Tests](#Running-the-Tests )**<br>
* **[Test Report Generation](#Test-Report-Generation)**<br>

<br>

## Setting up Developement Environment 

### Project Pre-Installation

#### Dependency handling
All Dependencies are handled by Maven

#### Required software to run tests from Command Line
* [Java 8 SE Development Kit](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Apache Maven 3](http://maven.apache.org/download.cgi)

#### Required software to run tests from IDE
Install *Required software to run tests from IDE* 
* [Eclipse](https://www.eclipse.org/downloads/)
* Add TestNG plugin in Eclipse from eclipse market store


### Test Project Build

#### Steps
1. Clone the repository.
2. Go to ***hello-hf*** folder.
3. Import the project as maven project.

## Running the Tests 

#### Available test suites
- Web tests are configured to run via file - ***run_test_web.xml***
- Api tests are configured to run via file - ***run_test_api.xml***


#### Test run configuration

***config.properties***
<br>
It is possible to configure test run via **config.properties**

   	web.url = http://automationpractice.com/index.php
    web.isGridEnabled = false
    web.browser = chrome
    web.defaultTimeout = 20
    web.dataDir=src/test/java
    web.seleniumGrid=localhost
    
    
    # API Test Settings
    api.host = automationintesting.online
    api.port = 443
    api.protocol = https 


#### Command line way
It is also possible to trigger tests from command line.

##### Steps to run UI tests
1. Go to ***hello-hf*** folder.
2. Use the below command to run the Web tests.

     
        mvn clean test -Dsurefire.suiteXmlFiles=run_test_web.xml -Dbrowser=chrome

3. Browser to be run can be configured via parameter **-Dbrowser** in commandline or via **config.properties**


###### On Linux

Since mostly linux box doesn't have browsers configured. We prefer to run using ***selenium grid***.

* Configure the selenium grid.
* Add the below properties in the ***config.properties***
	
    
      #Selenium grid
      web.isGridEnabled = false
      web.seleniumGrid= <GridHost>:<gridport> # example 'localhost:4444'
 
 
 ##### Steps to run API tests
 1. Go to ***hello-hf*** folder.
 2. Use the below command to run the Web tests.
 
      
        mvn clean test -Dsurefire.suiteXmlFiles=run_test_api.xml



## Test Report Generation

### Using Allure
1. Run the command as shown in example below after running as test as above.
    
        
        mvn allure:report           # Generate the report
        mvn allure:serve            # Open the report on browser
        

    

