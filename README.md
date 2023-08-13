# TestNG Framework
Developed TestNg framework using java With class seggregated with BaseUtils,listeners,PageObjectClass.java,Test.java,Testng.xml,Pom.xml

- src
  - main/java
    
        -absractcom
              -CommonlyUsing.java
        -resources
              -ExtentReporterNG.java
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

  
CommonlyUsing:

           The methods which are commonly using like login and accesing the menu is kept in this java file,it will reduce the code reusuability
           
ExtentReporterNG:
           this class conatins the configuration of the **ExtentReport and ExtentSparkReporter** 

GlobalData:

           GlobalData this is the property file where we can easily access the browser setting(like chrome firefox), jdbc excel file name and other settings easily 

aunkumar/pageobject:

            In this package all elemnts been located to access and control easily by moudule wise,**findby** annotation is used

BaseTestCommon:

           This java class contains the common methods of java and selenium,whenever we need this method we can call from the test class.This can optimize the code effectively

Listeners:

          The listener class implements  **ITestListener** interface, we can generte report from the extent report whenever the test gets failed and used the threadlocal which control the thread

Retry:

        whenever the test gets failed then the failed test will be in **retry.txt** and we can execute the failed one alone next time rather executing all

LoginTest:

        Which contains the most commonly using methods like login and clicking menu to reduce the code reusabilty and **picoContainer** is used to reduce the object creating process for each class

CalendarTest:

        In this class Dataprovider are provided and after the execution the test will write in the excel whether the test is pass or fail accordingly 

TestOutput:

        After all test executed the report were stored in the testoutpt in te name what we are providing

Testng.xml:

        testng.xml used to can control the test with **groups,parameters,packages,class**
     


