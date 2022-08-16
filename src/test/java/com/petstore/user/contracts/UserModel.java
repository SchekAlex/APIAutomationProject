package com.petstore.user.contracts;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonInclude;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int userStatus;

    private UserModel(){}

    public static class UserModelBuilder {
        public static UserModel buildEmptyUser(){return new UserModel();}

        public static UserModel buildFullUser(){
            return buildEmptyUser()
                    .setUsername("User1")
                    .setFirstName("Eu")
                    .setLastName("Tot Eu")
                    .setEmail("random@email.com")
                    .setPassword("randomp@ssw0rd")
                    .setPhone("0733444555");
        }
    }
}
