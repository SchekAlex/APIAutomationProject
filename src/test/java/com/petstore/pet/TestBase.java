package com.petstore.pet;

import com.petstore.pet.petservice.PetService;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
    protected static final PetService petApiService = PetService.getInstance();

    /* Variables for making requests*/
    public String petServiceBaseUrl;


    @BeforeSuite
    public void beforeMethod(){
        readAppProperties();
        readTestProperties();
    }
    private void readAppProperties(){
        FileInputStream fis = null;
        Properties testProperties = null;

        try{
            fis = new FileInputStream("src/test/resources/configs/pet/app.properties");
            testProperties = new Properties();
            testProperties.load(fis);
        } catch (Exception e){
            e.printStackTrace();
        }

        petServiceBaseUrl = testProperties.getProperty("baseUrl");

        try {
            if(fis!=null){
                fis.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void readTestProperties(){
        FileInputStream fis =null;
        Properties testProperties = null;

        try{
            fis = new FileInputStream("src/test/resources/configs/pet/test.properties");
            testProperties = new Properties();
            testProperties.load(fis);
        }catch (Exception e){
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
}
