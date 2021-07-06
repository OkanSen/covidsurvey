# covidsurvey


Android Studio Setup

To set up the android application project on PC, you should simply extract the zip to
where Android Studio saves are kept in your local directory. Once the project is
loaded you should go 
File -> Project Structure -> Dependencies -> Add Dependency -> Library Dependency

Then search for "glide"

Among the results, choose 
"com.github.bumptech.glide" as ID
with "glide" as Artifact Name
"Maven Central, JCenter" as Repository
4.12.0 Version

The project can now be tested within Android Studio with an emulator or physical device
-----------------
The apk is also stored inside Appium folder which can be manually installed to any phone.

----------------------------
Appium Testing

The Appium test folder can also be dragged and dropped to where your tests are stored at.

----------------------------
The demo video covers all test cases. The XML file stored inside the phone in private mode
can only be viewed within Android Studio. It is stored in 
"data->data->com.svavers.covidsurvey->shared_prefs->com.svavers.covidsurvey.xml"





Github Page: https://github.com/OkanSen/covidsurvey






