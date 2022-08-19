package com.petstore.user;

import com.petstore.user.contracts.UserModel;
import com.petstore.user.userservice.UserService;
import io.restassured.response.Response;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestBase {
    protected static final UserService userApiService = UserService.getInstance();

    protected static List<UserModel> userModelListTestBase = new ArrayList<>();
    /* Variables to store test data from the properties file*/
    public static String username;
    public static String firstName;
    public static String lastName;
    public static String email;
    public static String password;
    public static String phone;

    /* For making requests */
    public static String userServiceBaseUrl;

    @BeforeSuite
    public void beforeSuiteMethod() {
        readAppProperties();
        readTestProperties();
        createUsersForTests();
    }

    @AfterSuite
    public void afterSuiteMethod() {
        logoutUser();
    }

    private void createUsersForTests() {
        userModelListTestBase.add(UserModel.UserModelBuilder.buildEmptyUser()
                .setUsername(username)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(password)
                .setPhone(phone));

        Response createWithArrayUser = userApiService.postCreateWithArray(userModelListTestBase);

        assertThat("Wrong status code, expected 200, but was: " + createWithArrayUser.getStatusCode(),
                createWithArrayUser.getStatusCode(), equalTo(200));
        assertThat("Content is the correct type", createWithArrayUser.getContentType(), equalTo("application/json"));


    }

    private void logoutUser() {
        userApiService.logoutUser();
    }

    /* Taking values from the properties file */
    private void readAppProperties() {
        FileInputStream fis = null;
        Properties testProperties = null;

        try {
            fis = new FileInputStream("src/test/resources/configs/user/app.properties");
            testProperties = new Properties();
            testProperties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }

        userServiceBaseUrl = testProperties.getProperty("baseUrl");

        try {
            if (fis != null) {
                fis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readTestProperties() {
        FileInputStream fis = null;
        Properties testProperties = null;

        try {
            fis = new FileInputStream("src/test/resources/configs/user/test.properties");
            testProperties = new Properties();
            testProperties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }

        username = testProperties.getProperty("username");
        firstName = testProperties.getProperty("firstName");
        lastName = testProperties.getProperty("lastName");
        email = testProperties.getProperty("email");
        password = testProperties.getProperty("password");
        phone = testProperties.getProperty("phone");

        try {
            if (fis != null) {
                fis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
