package com.petstore.pet.petservice;

import com.petstore.pet.contracts.PetModel;
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
import org.codehaus.groovy.runtime.callsite.PerInstancePojoMetaClassSite;
import org.junit.runner.Request;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;

public class PetService {

    private static final PetService petServiceInstance = new PetService();
    private static final Logger logger = LogManager.getLogger();

    private PetService(){
    }

    public static PetService getInstance(){
        return petServiceInstance;
    }

    /**
     *
     * @param id
     * @param additionalMetadata
     * @param file
     * @return
     */
    public Response postUploadImage(Integer id, String additionalMetadata, File file){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.pathParam("petId",id);
        requestSpecification.formParam("additionalMetadata", additionalMetadata);
        requestSpecification.formParam("file", file);

        return petApiRequest("/{petId}/uploadImage",Method.POST,requestSpecification)
                .then().extract().response();
    }

    /**
     *
     * @param petModel
     * @return
     */
    public Response postPetToStore(PetModel petModel){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(petModel);

        return petApiRequest("",Method.POST, requestSpecification)
                .then().extract().response();
    }

    /**
     *
     * @param petId
     * @param name
     * @param status
     * @return
     */
    public Response postPetId(Integer petId, String name, String status){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.pathParam("petId", petId);
        requestSpecification.formParam("name", name);
        requestSpecification.formParam("status", status);

        return petApiRequest("/{petId}", Method.POST, requestSpecification)
                .then().extract().response();

    }

    /**
     *
     * @param petModel
     * @return
     */
    public Response putPetUpdate(PetModel petModel){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.contentType(ContentType.JSON);

        return petApiRequest("", Method.PUT, requestSpecification)
                .then().extract().response();
    }

    /**
     *
     * @param listOfPetModel
     * @param status
     * @return
     */
    public Response getFindPetByStatus(List<PetModel> listOfPetModel,List<String> status){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.queryParam("status", status);
        requestSpecification.body(listOfPetModel);

        return petApiRequest("/findByStatus", Method.GET,requestSpecification)
                .then().extract().response();
    }

    /**
     *
     * @param petId
     * @return
     */
    public Response getFindPetById(Integer petId){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.pathParam("petId", petId);

        return petApiRequest("/{petId}",Method.GET, requestSpecification)
                .then().extract().response();

    }

    /**
     *
     * @param api_key
     * @param petId
     * @return
     */
    public Response deletePet(String api_key,Integer petId){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("api_key",api_key);
        requestSpecification.pathParam("petId",petId);

        return petApiRequest("/{petId}",Method.GET,requestSpecification)
                .then().extract().response();
    }

    /**
     * Main method for making requests with Rest Assured to "UserServiceApi" User Calls
     *
     * @param methodName           - the name of the method used for the request(Ex: /user/createWithArray)
     * @param httpMethod           - the type of the HTTP method used for the request (Ex: GET/POST)*
     * @param requestSpecification additional Request Specification to be used in the request (Ex: body data)
     * @return {@Link Response}
     */
    private Response petApiRequest(String methodName, Method httpMethod, RequestSpecification requestSpecification){
        final String petServiceApiBaseUrl = "";
        logger.info("Request Base URL: " + httpMethod + " " + petServiceApiBaseUrl+ "/" + methodName);

        requestSpecification.header("X-TrackerContext-Caller", "PetServiceApiTesting");
        requestSpecification.header("X-TrackerContext-CallerIdentifier", "ApiTesting-PetService");

        return given()
                .filter(new AllureRestAssured())
                .filter(new RequestLoggingFilter(System.out))
                .filter(new ResponseLoggingFilter(System.out))
                .spec(requestSpecification)
                .baseUri(petServiceApiBaseUrl)
                .when().request(httpMethod,methodName)
                .then().extract().response();
    }
}
