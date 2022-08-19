package com.petstore.user.tests;

import com.petstore.user.TestBase;
import com.petstore.user.contracts.UserModel;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class PutUpdateUser extends TestBase {

    List<UserModel> listOfUserModels = new ArrayList<>();

    @Test(description = "Update a user and verify the modifications")
    public void putUpdateUser_checkResponseOk() {
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

        List<UserModel> listOfUsersToBeChanged = listOfUserModels;

        listOfUsersToBeChanged.get(0).setUsername("ChangedName").setFirstName("Changed").setLastName("Name");

        Response updateUser = userApiService.putUpdateUser(listOfUsersToBeChanged, listOfUserModels.get(0).getUsername());

        assertThat("Wrong status code,expected 200, but was: " + updateUser.getStatusCode(),
                updateUser.getStatusCode(), equalTo(200));
        assertThat("Content is the correct type.",
                updateUser.getContentType(), equalTo("application/json"));

        UserModel updatedUser = updateUser.as(UserModel.class);

        assertThat("Check that the user status value is the same",
                updatedUser.getUserStatus(), equalTo(listOfUserModels.get(0).getUserStatus()));
        assertThat("Check that the email value is the same",
                updatedUser.getEmail(), equalTo(listOfUserModels.get(0).getEmail()));
        assertThat("Check that the username value is the same",
                updatedUser.getUsername(), not(listOfUserModels.get(0).getUsername()));
        assertThat("Check that the first name value is the same",
                updatedUser.getFirstName(), not(listOfUserModels.get(0).getFirstName()));
        assertThat("Check that the last name value is the same",
                updatedUser.getLastName(), not(listOfUserModels.get(0).getLastName()));
        assertThat("Check that the password value is the same",
                updatedUser.getPassword(), equalTo(listOfUserModels.get(0).getPassword()));
        assertThat("Check that the phone value is the same",
                updatedUser.getPhone(), equalTo(listOfUserModels.get(0).getPhone()));
    }

    //At the time of the first run the method gave code 500
}
