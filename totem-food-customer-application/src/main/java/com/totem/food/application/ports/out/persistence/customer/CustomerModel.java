package com.totem.food.application.ports.out.persistence.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerModel {

    private String id;
    private String name;
    private String cpf;
    private String email;
    private String mobile;
    private String password;
    private ZonedDateTime modifiedAt;
    private ZonedDateTime createAt;

    public static CustomerModel getForDevProfile(){
        return new CustomerModel(
                "c8d80736-97d4-4993-b963-a88f6e314675",
                "e2e-name",
                "12345678901",
                "e2e@email.com",
                "00999999999",
                "123456",
                ZonedDateTime.now().plusMinutes(10),
                ZonedDateTime.now()
        );
    }
}
