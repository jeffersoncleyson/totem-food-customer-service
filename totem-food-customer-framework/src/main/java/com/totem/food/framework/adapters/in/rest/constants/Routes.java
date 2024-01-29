package com.totem.food.framework.adapters.in.rest.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Routes {

    //######## VERSIONS
    public static final String API_VERSION_1 = "/v1";

    //######## CUSTOMER
    public static final String ADM_CUSTOMER = "/administrative/customer";
    public static final String TOTEM_CUSTOMER = "/totem/customer";
    public static final String CUSTOMER_ID = "/{cpf}";
    public static final String CONFIRM_CUSTOMER = "/code/{code}";

    //######## ORDER
    public static final String TOTEM_ORDER = "/totem/order";

}
