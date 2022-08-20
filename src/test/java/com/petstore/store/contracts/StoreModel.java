package com.petstore.store.contracts;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Date;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreModel {
    private Integer id;
    private Integer petId;
    private Integer quantity;
    private String shipDate;
    private String status;
    private Boolean complete;

    private StoreModel(){}

    public static class StoreModelBuilder{

        public static StoreModel buildEmptyStore(){return new StoreModel();}

        public static StoreModel buildFullStore(){
            return buildEmptyStore()
                    .setId(0)
                    .setPetId(0)
                    .setQuantity(1)
                    .setShipDate(OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-mm-dd'T'HH:mm:ss")))
                    .setStatus("available")
                    .setComplete(true);
        }
    }
}
