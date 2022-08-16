package com.petstore.user.tests;

import com.petstore.user.TestBase;
import com.petstore.user.contracts.UserModel;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PostUserCreateWithArrayAndList extends TestBase {

    @Test(description = "PostUserCreateWithArray and check response")
    public void postCreateWithArray_checkResponseOk(){
        Response postUserResponse = userApiService.postCreateWithArray(UserModel.UserModelBuilder.buildFullUser());

        assertThat("Wrong status code, expected 200, but was: " + postUserResponse.getStatusCode(),
                postUserResponse.getStatusCode(), equalTo(200));


    }
}
