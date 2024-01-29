package com.totem.food.framework.adapters.in.rest.customer.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.totem.food.application.ports.in.dtos.customer.CustomerDto;
import com.totem.food.application.ports.in.dtos.customer.CustomerFilterDto;
import com.totem.food.application.usecases.commons.ISearchUseCase;
import com.totem.food.framework.test.utils.TestUtils;
import lombok.SneakyThrows;
import mocks.dtos.CustomerDtoMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.ZonedDateTime;
import java.util.List;

import static com.totem.food.framework.adapters.in.rest.constants.Routes.ADM_CUSTOMER;
import static com.totem.food.framework.adapters.in.rest.constants.Routes.API_VERSION_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class AdministrativeCustomerRestApiAdapterTest {

    @Mock
    private ISearchUseCase<CustomerFilterDto, List<CustomerDto>> iSearchCustomerUseCase;

    private MockMvc mockMvc;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        final var administrativeCustomerRestApiAdapter = new AdministrativeCustomerRestApiAdapter(iSearchCustomerUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(administrativeCustomerRestApiAdapter).build();
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        closeable.close();
    }

    @ParameterizedTest
    @ValueSource(strings = API_VERSION_1 + ADM_CUSTOMER)
    void testListAllWhenPassedBody(String endpoint) throws Exception {

        //### Mocks - Objects and Values
        final var customerDto = CustomerDtoMock.getMock();
        final var customerFilterDto = new CustomerFilterDto("John");

        //## Given
        when(iSearchCustomerUseCase.items(any(CustomerFilterDto.class))).thenReturn(List.of(customerDto));

        final String jsonRequest = TestUtils.toJSON(customerFilterDto).orElseThrow();
        final MockHttpServletRequestBuilder mockHttp = MockMvcRequestBuilders.get(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        //## When
        final ResultActions resultAction = mockMvc.perform(mockHttp);

        //## Then
        resultAction.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        final String responseJson = resultAction.andReturn().getResponse().getContentAsString();
        final List<CustomerDto> responseEntity = TestUtils.toTypeReferenceObject(responseJson, new TypeReference<List<CustomerDto>>() {
        }).orElseThrow();

        assertThat(responseEntity)
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(ZonedDateTime.class)
                .isEqualTo(List.of(customerDto));

        verify(iSearchCustomerUseCase, times(1)).items(any(CustomerFilterDto.class));

    }

    @ParameterizedTest
    @ValueSource(strings = API_VERSION_1 + ADM_CUSTOMER)
    void testListAllWhenEmptyBody(String endpoint) throws Exception {

        //### Mocks - Objects and Values
        final var customerDto = CustomerDtoMock.getMock();

        //## Given
        when(iSearchCustomerUseCase.items(null)).thenReturn(List.of(customerDto));

        final MockHttpServletRequestBuilder mockHttp = MockMvcRequestBuilders.get(endpoint)
                .contentType(MediaType.APPLICATION_JSON);

        //## When
        final ResultActions resultAction = mockMvc.perform(mockHttp);

        //## Then
        resultAction.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        final String responseJson = resultAction.andReturn().getResponse().getContentAsString();
        final List<CustomerDto> responseEntity = TestUtils.toTypeReferenceObject(responseJson, new TypeReference<List<CustomerDto>>() {
        }).orElseThrow();

        assertThat(responseEntity)
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(ZonedDateTime.class)
                .isEqualTo(List.of(customerDto));

        verify(iSearchCustomerUseCase, times(1)).items(null);

    }

}