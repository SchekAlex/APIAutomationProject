package com.petstore.user.tests;

import com.petstore.user.TestBase;
import com.petstore.user.contracts.UserModel;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

public class DeleteUsers extends TestBase {

    List<UserModel> listOfUserModels = new ArrayList<>();

    @Test(description = "Delete user and check if the user was deleted")
    public void deleteUser() {
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

        Response deleteUser = userApiService.deleteUser(listOfUserModels.get(0).getUsername());

        assertThat("Wrong status code,expected 200, but was: " + createUser.getStatusCode(),
                deleteUser.getStatusCode(), equalTo(200));
        assertThat("Content is the correct type.",
                deleteUser.getContentType(), equalTo("application/json"));
        assertThat("Check the body to contain the right user",
                deleteUser.getBody().asString(), equalTo("{\"code\":200,\"type\":\"unknown\",\"message\":\"" + listOfUserModels.get(0).getUsername() + "\"}"));

        Response searchForUser = userApiService.getUser(listOfUserModels.get(0).getUsername());

        assertThat("Wrong status code,expected 200, but was: " + createUser.getStatusCode(),
                searchForUser.getStatusCode(), equalTo(404));
        assertThat("Content is the correct type.",
                searchForUser.getContentType(), equalTo("application/json"));
        assertThat("Check the body",
                searchForUser.getBody().asString(), equalTo("{\"code\":1,\"type\":\"error\",\"message\":\"User not found\"}"));
    }
}
