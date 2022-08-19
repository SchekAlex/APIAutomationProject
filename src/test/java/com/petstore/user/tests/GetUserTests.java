package com.petstore.user.tests;

import com.petstore.user.TestBase;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class GetUserTests extends TestBase {


    /* Negative Tests */
    @Test(description = "Check that for an non-existent user the status code is 404")
    public void getNonExistingUser_checkResponseOk() {
        Response userResponse = userApiService.getUser("user" + Math.random());

        assertThat("Wrong status code, expected 404, but was: " + userResponse.getStatusCode(),
                userResponse.getStatusCode(), equalTo(404));

        assertThat("Wrong body, expected: '{\"code\":1,\"type\":\"error\",\"message\":\"User not found\"}' , but was: " + userResponse.getBody().asString(),
                userResponse.getBody().asString(), equalTo("{\"code\":1,\"type\":\"error\",\"message\":\"User not found\"}"));
    }
}
