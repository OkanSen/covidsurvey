
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;


public class AppiumTest {

    static DesiredCapabilities cap = new DesiredCapabilities();
    static AndroidDriver<MobileElement> driver;

    public static void main(String[] args) {

        try {
            System.out.println("Test Case 3(installing the apk)");
            installCovidSurvey(); //part of test case 3
            System.out.println("Running Test Case 2(Empty field disables submit button");
            testCase2();
            System.out.println("Running Test Case 1 / Test Case 3(Switching apps)");
            testCase1();
            System.out.println("Running Test Case 4 (User will stay on the success page until he retries");
            testCase4();
            testCase5Rotation();
            Thread.sleep(2000);
            driver.rotate(ScreenOrientation.PORTRAIT);

        } catch (Exception exp) {
            System.out.println(exp.getCause());
            System.out.println(exp.getMessage());
            exp.printStackTrace();

        }


    }

    //THIS DEALS WITH TEST CASE 3 and installs the app
    public static void installCovidSurvey() throws Exception {




        cap.setCapability("deviceName", "fc72616"); //android device id being used
        cap.setCapability("platformName", "android"); //implemented on android
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        cap.setCapability("app", "C:\\Users\\Hassam\\Desktop\\Appium\\Covid_Survey.apk"); //this directory will need to be modfied
        cap.setCapability("appPackage", "com.svavers.covidsurvey");
        cap.setCapability("appActivity", "com.example.covidsurvey.ScrollingActivity");
        cap.setCapability("skipDeviceInitialization", true);
        cap.setCapability("skipServerInstallation", true);
        cap.setCapability("noReset", true);
        cap.setCapability("dontStopAppOnReset", true);
        URL url = new URL("http://127.0.0.1:4723/wd/hub"); //appium server being used
        driver = new AndroidDriver<MobileElement>(url, cap);
        System.out.println("Covid Survey Application has started");

    }

    public static void switchApps()
        {
            //App1 capabilities
            String covidAppPackageName = "com.svavers.covidsurvey";
            String covidActivityName = "com.example.covidsurvey.ScrollingActivity";

            // App2 capabilities
            String browserAppPackageName="com.koushikdutta.vysor";
            String browserAppActivityName=".StartActivity";

            //launch settings App
            driver.startActivity(new Activity(browserAppPackageName, browserAppActivityName));

            driver.findElement(By.id("com.koushikdutta.vysor:id/next")).click();



            driver.launchApp();


    }

    //IDS of elements in the app
    //ERROR TEXTBOX: com.example.myapplication:id/error1
    // FIRST NAME: com.example.myapplication:id/editTextTextPersonName
    // Surname: com.example.myapplication:id/editTextTextPersonName2
    // City: com.example.myapplication:id/editTextTextPersonName3
    // Gender (SWITCH): com.example.myapplication:id/switch1
    // Vaccine Drop Down(SPINNER): com.example.myapplication:id/spinner
    //Submit button: com.example.myapplication:id/button


    //test case 2 where empty fields would disable the submit button completely
    public static void testCase2(){
        //the fields in the app will be empty at this point and the submit button should not be clickable

        scrollByID("com.svavers.covidsurvey:id/button", 0);
        MobileElement el5 = driver.findElementById("com.svavers.covidsurvey:id/button");

        el5.click();

        if(el5.isEnabled()){
            System.out.println("The button is disabled, empty test case is working properly");
        }
    }


    //This is testcase 1 dealing with invalid credentials and has partially testcase 3 by implementing app switching
    public static void testCase1(){
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.SECONDS);



        //now fields will be not empty and will be filled

        //Now go back upto name field
        scrollByID("com.svavers.covidsurvey:id/editTextTextPersonName", 0);
        MobileElement el1 = driver.findElementById("com.svavers.covidsurvey:id/editTextTextPersonName");

        //Try first name has integer
        String name = "Jonathan*-123";
        String surname = "Davis//123";
        String city = "Ankara0Hello";

        System.out.println("Now entering the following fields, name, surname, city: "  + name + ", " + surname + ", " + city);

        //FIRST EXAMPLE ALL FIELDS ARE INVALID
        el1.sendKeys(name);

        MobileElement el2 = driver.findElementById("com.svavers.covidsurvey:id/editTextTextPersonName2");
        el2.sendKeys(surname);

        MobileElement el3 = driver.findElementById("com.svavers.covidsurvey:id/editTextTextPersonName3");
        el3.sendKeys(city);

        //DURING TYPING LETS SWITCH TO ANOTHER APP
        switchApps();
        //NOW SWITCHED BACK TO THIS APP
        MobileElement el4 = driver.findElementById("com.svavers.covidsurvey:id/switch1");
        el4.click();

        MobileElement spinner = driver.findElementById("com.svavers.covidsurvey:id/spinner");
        spinner.click();

        MobileElement selection = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.TextView[4]");
        selection.click();

        MobileElement calendar = driver.findElementById("com.svavers.covidsurvey:id/date");
        calendar.click();

        for(int i = 0;  i < 5 ; i++) {
            verticalSwipeByPercentages(0.5,0.7,0.3);
        }
        for(int i = 0;  i < 2 ; i++) {
            verticalSwipeByPercentages(0.5,0.7,0.45);
        }
        for(int i = 0;  i < 8 ; i++) {
            verticalSwipeByPercentages(0.5,0.7,0.7);
        }
        MobileElement exitCalendar = driver.findElementById("android:id/button1");
        exitCalendar.click();
        //android:id/button1

        scrollByID("com.svavers.covidsurvey:id/spinner2", 0);
        MobileElement effectSpinner = driver.findElementById("com.svavers.covidsurvey:id/spinner2");
        effectSpinner.click();

        MobileElement symptom = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.TextView[7]");
        symptom.click();



        //scroll till submit button is visible on screen
        scrollByID("com.svavers.covidsurvey:id/button", 0);
        MobileElement el5 = driver.findElementById("com.svavers.covidsurvey:id/button");
        el5.click();





        MobileElement el6 = driver.findElementById("com.svavers.covidsurvey:id/error1");
        System.out.println("The response from the error message is: " +  el6.getText());

        //SECOND EXAMPLE NAME AND CITY FIELD ARE VALID BUT SURNAME IS INCORRECT
        //Now go back to name element
        scrollByID("com.svavers.covidsurvey:id/editTextTextPersonName", 0);
        name = "Jonathan";
        city = "Ankara";
        System.out.println("Now entering the following fields, name, surname, city: "  + name + ", " + surname + ", " + city);



        //fixed the name and city but not surname lets see

        //clear the name and city fields
        el1.clear();
        el3.clear();

        //send the fixed keys now
        el1.sendKeys(name);
        el2.sendKeys(surname);
        el3.sendKeys(city);

        //scroll till submit button is visible on screen
        scrollByID("com.svavers.covidsurvey:id/button", 0);
        el5.click();

        //check out what the error message says
        System.out.println("The response from the error message is: " +  el6.getText());

        //THIRD EXAMPLE ALL FIELDS ARE VALID
        //Now go back to name element
        scrollByID("com.svavers.covidsurvey:id/editTextTextPersonName2", 0);
        //finally fix the surname field as well
        surname = "Davis";
        System.out.println("Now entering the following fields, name, surname, city: "  + name + ", " + surname + ", " + city);


        //clear the surname field
        el2.clear();
        //send the fixed surname now
        el2.sendKeys(surname);


        //scroll till submit button is visible on screen
        scrollByID("com.svavers.covidsurvey:id/button", 0);
        el5.click();
        //at this point we still havent uploaded an image so we will still get an error

        //check out what the error message says
        System.out.println("The response from the error message is: " +  el6.getText());

        testCase5();

        //scroll till submit button is visible on screen
        scrollByID("com.svavers.covidsurvey:id/button", 0);
        el5.click();

        //at this point we will be on the success acitivty


    }

    public static void testCase5Rotation(){
        driver.rotate(ScreenOrientation.LANDSCAPE);

        //Now go back upto name field
        scrollByID("com.svavers.covidsurvey:id/editTextTextPersonName", 0);
        MobileElement el1 = driver.findElementById("com.svavers.covidsurvey:id/editTextTextPersonName");

        //Try first name has integer
        String name = "Jonathan";
        String surname = "Davis";
        String city = "Ankara";

        System.out.println("Now entering the following fields, name, surname, city: "  + name + ", " + surname + ", " + city);

        //FIRST EXAMPLE ALL FIELDS ARE INVALID
        el1.sendKeys(name);

        MobileElement el2 = driver.findElementById("com.svavers.covidsurvey:id/editTextTextPersonName2");
        el2.sendKeys(surname);

        scrollByID("com.svavers.covidsurvey:id/editTextTextPersonName3", 0);
        MobileElement el3 = driver.findElementById("com.svavers.covidsurvey:id/editTextTextPersonName3");
        el3.sendKeys(city);

        scrollByID("com.svavers.covidsurvey:id/switch1", 0);
        MobileElement el4 = driver.findElementById("com.svavers.covidsurvey:id/switch1");
        el4.click();

        scrollByID("com.svavers.covidsurvey:id/spinner", 0);
        MobileElement spinner = driver.findElementById("com.svavers.covidsurvey:id/spinner");
        spinner.click();

        MobileElement selection = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.TextView[4]");
        selection.click();

        scrollByID("com.svavers.covidsurvey:id/date", 0);
        MobileElement calendar = driver.findElementById("com.svavers.covidsurvey:id/date");
        calendar.click();

        for(int i = 0;  i < 1 ; i++) {
            verticalSwipeByPercentages(0.5,0.7,0.3);
        }
        for(int i = 0;  i < 3 ; i++) {
            verticalSwipeByPercentages(0.5,0.7,0.45);
        }
        for(int i = 0;  i < 5 ; i++) {
            verticalSwipeByPercentages(0.5,0.7,0.7);
        }
        MobileElement exitCalendar = driver.findElementById("android:id/button1");
        exitCalendar.click();
        //android:id/button1

        scrollByID("com.svavers.covidsurvey:id/spinner2", 0);
        MobileElement effectSpinner = driver.findElementById("com.svavers.covidsurvey:id/spinner2");
        effectSpinner.click();

        MobileElement symptom = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.TextView[2]");
        symptom.click();


        testCase5();

        //scroll till submit button is visible on screen
        scrollByID("com.svavers.covidsurvey:id/button", 0);
        MobileElement el5 = driver.findElementById("com.svavers.covidsurvey:id/button");
        el5.click();


        //at this point we will be on the success acitivty


    }


    //This deals with uploading the users image and checking if its uploaded correctly
    public static void testCase5()
    {
        scrollByID("com.svavers.covidsurvey:id/buttonLoadPicture", 0);
        MobileElement el1 = driver.findElementById("com.svavers.covidsurvey:id/buttonLoadPicture");
        el1.click();
        MobileElement el2 = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.GridView/android.widget.LinearLayout[1]/android.widget.FrameLayout/android.widget.ImageView");
        el2.click();
        MobileElement el3 = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout[1]/android.view.ViewGroup/android.view.ViewGroup/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.TextView");
        el3.click();
        MobileElement el4 = driver.findElementByXPath("//android.widget.ImageView[@content-desc=\"All photos4539\"]");
        el4.click();
        MobileElement el5 = driver.findElementByXPath("//android.widget.FrameLayout[@content-desc=\"Photo taken on 5 July 2021 23:03:50\"]/android.widget.FrameLayout/android.widget.TextView");
        el5.click();


    }


    //this checks if the app is opening success page still and showing data correctly after the app is closed
    public static void testCase4() throws InterruptedException {
        Thread.sleep(3000);
        String covidAppPackageName = "com.svavers.covidsurvey";
        String covidActivityName = "com.example.covidsurvey.ScrollingActivity";
        driver.closeApp();

        driver.launchApp();

        Thread.sleep(3000);
        MobileElement db = driver.findElementById("com.svavers.covidsurvey:id/cleardb");
        db.click();
        MobileElement reset = driver.findElementById("com.svavers.covidsurvey:id/resetbtn");
        reset.click();

    }



    //This method scrolls the app till element is found on the screen
    public static void scrollByID(String Id, int index) {

        try {

            driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\""+Id+"\").instance("+index+"));"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void verticalSwipeByPercentages(double startPercentage, double endPercentage, double anchorPercentage) {
        Dimension size = driver.manage().window().getSize();
        int anchor = (int) (size.width * anchorPercentage);
        int startPoint = (int) (size.height * startPercentage);
        int endPoint = (int) (size.height * endPercentage);
        new TouchAction(driver)
                .press(point(anchor, startPoint))
                .waitAction(waitOptions(ofMillis(1000)))
                .moveTo(point(anchor, endPoint))
                .release().perform();
    }




}
