package com.totem.food.framework.adapters.out.web.cognito.mocks;

import com.totem.food.application.ports.out.persistence.customer.CustomerModel;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class CustomerEntityMock {

    public static CustomerModel getMock() {
        var model = new CustomerModel();
        model.setId("123");
        model.setName("Name");
        model.setCpf("12312312399");
        model.setEmail("name@name.com");
        model.setMobile("+5511900112233");
        model.setModifiedAt(ZonedDateTime.now(ZoneOffset.UTC));
        model.setCreateAt(ZonedDateTime.now(ZoneOffset.UTC));
        return model;
    }
}
