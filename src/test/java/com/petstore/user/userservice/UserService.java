package com.petstore.user.userservice;

import com.petstore.user.TestBase;
import com.petstore.user.contracts.UserModel;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.authentication;
import static io.restassured.RestAssured.given;


public class UserService {
    private static final UserService userServiceInstance = new UserService();
    private static final Logger logger = LogManager.getLogger();
    private UserService(){}

    public static UserService getInstance(){return userServiceInstance;}



    /**
     * Method - POST /user/createWithList
     * @param userModel
     * @return {@Link Response}
     */
    public Response postCreateWithList(List<UserModel> userModel){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(userModel);

        return userApiRequest("/createWithList", Method.POST, requestSpecification)
                .then().extract().response();
    }
    /**
     * Method - POST /user/createWithArray
     * @param userModel - an ArrayList of userModel defined in the contracts package
     * @return {@Link Response}
     */
    public Response postCreateWithArray(List<UserModel> userModel){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(userModel);

        return userApiRequest("/createWithArray", Method.POST, requestSpecification)
                .then().extract().response();
    }

    /**
     * Method - POST /user - create User only while logged in
     * @param userModel
     * @return
     */
    public Response postCreateUser (UserModel userModel){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(userModel);

        return userApiRequest("",Method.POST,requestSpecification)
                .then().extract().response();
    }
    /**
     * Method - PUT /user/{username}
     * @param userModel
     * @return
     */
    public Response putUpdateUser(List<UserModel> userModel,String username){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.pathParam("username", username);
        requestSpecification.body(userModel);

        return userApiRequest("/{username}", Method.PUT, requestSpecification)
                .then().extract().response();
    }

    /**
     * Method - DELETE /user/{username}
     * @param username
     * @return
     */
    public Response deleteUser(String username){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.pathParam("username", username);

        return userApiRequest("/{username}",Method.DELETE, requestSpecification)
                .then().extract().response();
    }

    /**
     * Method - GET /user/login
     * @param username
     * @param password
     * @return
     */
    public Response loginUser(String username, String password){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.queryParam("username", username);
        requestSpecification.queryParam("password", password);

        return userApiRequest("/login", Method.GET, requestSpecification)
                .then().extract().response();
    }

    /**
     * Method - GET /user/logout
     * @return
     */
    public Response logoutUser(){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.contentType(ContentType.JSON);

        return userApiRequest("/logout", Method.GET, requestSpecification)
                .then().extract().response();
    }

    /**
     * Method - GET user/{username} - method that returns the details of an user based on the given String for the pathParam
     * @param username
     * @return {@Link Response}
     */
    public Response getUser(String username){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.pathParam("username",username);

        return userApiRequest("/{username}",Method.GET, requestSpecification)
                .then().extract().response();
    }
    /**
     * Main method for making requests with Rest Assured to "UserServiceApi" User Calls
     * @param methodName - the name of the method used for the request(Ex: /user/createWithArray)
     * @param httpMethod  - the type of the HTTP method used for the request (Ex: GET/POST)*
     * @param requestSpecification additional Request Specification to be used in the request (Ex: body data)
     * @return {@Link Response}
     */
    private Response userApiRequest(String methodName, Method httpMethod, RequestSpecification requestSpecification){
        final String userServiceApiBaseUrl = TestBase.userServiceBaseUrl;
        logger.info("Request Base URL: "+ httpMethod + " " + userServiceApiBaseUrl + "/" + methodName);

        requestSpecification.header("X-TrackerContext-Caller","UserServiceApiTesting");
        requestSpecification.header("X-TrackerContext-CallerIdentifier", "ApiTesting-UserService");

        return given()
                .filter(new AllureRestAssured())
                .filter(new RequestLoggingFilter(System.out))
                .filter(new ResponseLoggingFilter(System.out))
                .spec(requestSpecification)
                .baseUri(userServiceApiBaseUrl)
                .when().request(httpMethod,methodName)
                .then().extract().response();

    }
}
