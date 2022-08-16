package com.petstore.user;

import com.petstore.user.userservice.UserService;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
    protected static final UserService userApiService = UserService.getInstance();

    /* For making requests */
    public static String userServiceBaseUrl;

    /* Taking values from the properties file */
    private void readAppProperties(){
        FileInputStream fis = null;
        Properties testProperties = null;

        try{
            fis = new FileInputStream("src/test/resources/configs/user/app.properties");
            testProperties = new Properties();
            testProperties.load(fis);
        }catch(Exception e){
            e.printStackTrace();
        }

        userServiceBaseUrl = testProperties.getProperty("baseUrl");

        try{
            if(fis!=null){
                fis.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void readTestProperties(){
        FileInputStream fis = null;
        Properties testProperties = null;

        try{
            fis = new FileInputStream("src/test/resources/configs/user/test.properties");
            testProperties = new Properties();
            testProperties.load(fis);
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            if(fis!=null){
                fis.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @BeforeSuite
    public void beforeSuiteMethod(){
        readAppProperties();
        readTestProperties();
    }
}
