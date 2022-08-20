package com.petstore.pet.contracts;

import com.petstore.pet.TestBase;
import com.petstore.pet.petservice.PetService;
import com.petstore.store.contracts.StoreModel;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PetModel {
    private Integer id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tags> tags;
    private String status;

    private PetModel(){}

    public static class PetModelBuilder{

        public static PetModel buildEmptyPet(){return new PetModel();}

        public static PetModel buildFullPet(){
            return buildEmptyPet()
                    .setId(0)
                    .setName("RandomName" + Math.random())
                    .setStatus("available");

        }
    }
}
