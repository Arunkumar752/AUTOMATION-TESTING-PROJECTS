# AUTOMATION-TESTING-PROJECTS
Developed TestNg framework using java With class seggregated with common methods,listeners,Testng.xml,Pom

- src
  - main/java
        -absractcom
              -CommonlyUsing.java
        -resources
              -ExtentReporterNGG.java
              -GlobalData.properties
       -aunkumar/pageobject
              -ApplyLeave.java
              -Calendar.java
              -LoginPage.java
              -OlmsPage.java
  - test/java

        - arunkmar/testcompo
              -BaseTestCommon.java
              -Listeners.java
              -Retry.java
        - arunkumar/test
              -LoginTest.java
              -CalendarTest.java
              -CheckTest.java
  - resources
        -TestData.xlsx
  -TestOutput
       -ExtentReport.html
- testng.xml
- pom.xml

BaseTestCommon:
           This java class contains the common methods of java and selenium,whenever we need this method we can call from the test class.This can optimize the code effectively

Listeners:
          The listener class implements  ITestListener interface, we can generte report from the extent report whenever the test gets failed and used the threadlocal which control the thread

Retry:
        whenever the test gets failed then the failed test will be in retry.txt and we can execute the failed one alone next time rather executing all

LoginTest:
        Which contains the most commonly using methods like login and clicking menu to reduce the code reusabilty 

CalendarTest:
        In this class Dataprovider are provided and after the execution the test will write in the excel whether the test is pass or fail accordingly 

TestOutput:
        After all test executed the report were stored in the testoutpt in te name what we are providing

Testng.xml:
        testng.xml used to can control the test with groups,parameters,packages,class
     


