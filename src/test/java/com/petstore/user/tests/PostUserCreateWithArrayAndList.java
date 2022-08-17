package com.petstore.user.tests;

import com.petstore.user.TestBase;
import com.petstore.user.contracts.UserModel;
import io.restassured.response.Response;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PostUserCreateWithArrayAndList extends TestBase {
    List<UserModel> listOfUserModels = new ArrayList<>();

    @Test(description = "PostUserCreateWithArray and check response")
    public void postCreateWithArray_checkResponseOk(){
        listOfUserModels.add(UserModel.UserModelBuilder.buildFullUser());
        Response postUserResponse = userApiService.postCreateWithArray(listOfUserModels);

        assertThat("Wrong status code, expected 200, but was: " + postUserResponse.getStatusCode(),
                postUserResponse.getStatusCode(), equalTo(200));
        assertThat("Check that the response type is the correct one",
                postUserResponse.getContentType(), equalTo("application/json"));

        Response getUser = userApiService.getUser(listOfUserModels.get(0).getUsername());

        assertThat("Wrong status code, expected 200, but was: " + getUser.getStatusCode(),
                getUser.getStatusCode(), equalTo(200));
        assertThat("Check that the response type is the correct one",
                getUser.getContentType(), equalTo("application/json"));

        List<UserModel> getUserModelResponse = Arrays.asList(getUser.as(UserModel.class));

        assertThat("Check that the user status value is the same",
                listOfUserModels.get(0).getUserStatus(), equalTo(getUserModelResponse.get(0).getUserStatus()));
        assertThat("Check that the email value is the same",
                listOfUserModels.get(0).getEmail(), equalTo(getUserModelResponse.get(0).getEmail()));
        assertThat("Check that the username value is the same",
                listOfUserModels.get(0).getUsername(), equalTo(getUserModelResponse.get(0).getUsername()));
        assertThat("Check that the first name value is the same",
                listOfUserModels.get(0).getFirstName(), equalTo(getUserModelResponse.get(0).getFirstName()));
        assertThat("Check that the last name value is the same",
                listOfUserModels.get(0).getLastName(), equalTo(getUserModelResponse.get(0).getLastName()));
        assertThat("Check that the password value is the same",
                listOfUserModels.get(0).getPassword(), equalTo(getUserModelResponse.get(0).getPassword()));
        assertThat("Check that the phone value is the same",
                listOfUserModels.get(0).getPhone(), equalTo(getUserModelResponse.get(0).getPhone()));
    }

}
