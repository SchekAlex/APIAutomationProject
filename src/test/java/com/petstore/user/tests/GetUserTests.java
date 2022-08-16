package com.petstore.user.tests;

import com.petstore.user.TestBase;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class GetUserTests extends TestBase {




    /* Negative Tests */
    @Test(description = "Check that for an non-existent user the status code is 404")
    public void getNonExistingUser_checkResponseOk(){
        Response userResponse = userApiService.getUser("user1");

        assertThat("Wrong status code, expected 404, but was: " + userResponse.getStatusCode(),
                userResponse.getStatusCode(), equalTo(404));

        assertThat("Wrong body, expected: '<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><apiResponse><message>null for uri: http://petstore.swagger.io/v2/user/user/user1</message><type>unknown</type></apiResponse>' , but was: " + userResponse.getBody().asString(),
                userResponse.getBody().asString(), equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><apiResponse><message>null for uri: http://petstore.swagger.io/v2/user/user/user1</message><type>unknown</type></apiResponse>"));
    }
}
