package com.petstore.user.tests;

import com.petstore.user.TestBase;
import com.petstore.user.contracts.UserModel;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetCreateUser extends TestBase {
    List<UserModel> listOfUserModels = new ArrayList<>();

    @Test(description = "Create a new user while logged in with the Automation User and check that all the data is correct")
    public void createUser_checkResponseOk() {
        listOfUserModels.add(UserModel.UserModelBuilder.buildFullUser());
        Response loginWithAutoUser = userApiService.loginUser(userModelListTestBase.get(0).getUsername(), userModelListTestBase.get(0).getPassword());

        assertThat("Wrong status code,expected 200, but was: " + loginWithAutoUser.getStatusCode(),
                loginWithAutoUser.getStatusCode(), equalTo(200));
        assertThat("Content is the correct type.",
                loginWithAutoUser.getContentType(), equalTo("application/json"));

        Response createUser = userApiService.postCreateUser(listOfUserModels.get(0));

        assertThat("Wrong status code,expected 200, but was: " + createUser.getStatusCode(),
                createUser.getStatusCode(), equalTo(200));
        assertThat("Content is the correct type.",
                createUser.getContentType(), equalTo("application/json"));

        Response getCreatedUser = userApiService.getUser(listOfUserModels.get(0).getUsername());

        assertThat("Wrong status code,expected 200, but was: " + loginWithAutoUser.getStatusCode(),
                createUser.getStatusCode(), equalTo(200));
        assertThat("Content is the correct type.",
                createUser.getContentType(), equalTo("application/json"));

        UserModel createdUserInTest = getCreatedUser.as(UserModel.class);

        assertThat("Check that the user status value is the same",
                createdUserInTest.getUserStatus(), equalTo(listOfUserModels.get(0).getUserStatus()));
        assertThat("Check that the email value is the same",
                createdUserInTest.getEmail(), equalTo(listOfUserModels.get(0).getEmail()));
        assertThat("Check that the username value is the same",
                createdUserInTest.getUsername(), equalTo(listOfUserModels.get(0).getUsername()));
        assertThat("Check that the first name value is the same",
                createdUserInTest.getFirstName(), equalTo(listOfUserModels.get(0).getFirstName()));
        assertThat("Check that the last name value is the same",
                createdUserInTest.getLastName(), equalTo(listOfUserModels.get(0).getLastName()));
        assertThat("Check that the password value is the same",
                createdUserInTest.getPassword(), equalTo(listOfUserModels.get(0).getPassword()));
        assertThat("Check that the phone value is the same",
                createdUserInTest.getPhone(), equalTo(listOfUserModels.get(0).getPhone()));
    }

//    @Test(description = "Create a user without another one logged in")
//    public void createUserWithoutOneLoggedIn_checkResponseOk(){
//        listOfUserModels.add(UserModel.UserModelBuilder.buildFullUser());
//
//        Response createUser = userApiService.postCreateUser(listOfUserModels.get(0));
//
//        assertThat("Wrong status code,expected 400, but was: " + createUser.getStatusCode(),
//                createUser.getStatusCode(), equalTo(400));
//        assertThat("Content is the correct type.",
//                createUser.getContentType(), equalTo("application/json"));
//        /*Apparently the app that I'm using for this framework allows the creation of the user without someone logged in,
//          even though it is mentioned in swagger that I can only do it logged in
//         */
//    }
}
