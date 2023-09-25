# APITestFramework
API Test Framework – Rest Assured and Cucumber 
Designed for testing API. 

**Some of the key features of this framework:**

-It generates Extent report with all the step details. Report will be generated both HTML & PDF file format.
-Generates execution logs, with detailed request and response details.
-Feature file has examples of reading request details from json and excel file.
-This also has an example to validate response body using json schema and java pojo classes.
-Test execution can be triggered form command line.
-Easy integration to CI/CD pipeline.

**Required Setup :**
-Java should be installed and configured.
-Maven should be installed and configured.

**Running Test:**
Open the command prompt and navigate to the folder in which pom.xml file is present. Run the below Maven command.

mvn clean test
mvn test –Dcucumber.options=”—tags @smoke”

Once the execution completes report & log will be generated in below folder.

Report: target/report
Log: target/logs
