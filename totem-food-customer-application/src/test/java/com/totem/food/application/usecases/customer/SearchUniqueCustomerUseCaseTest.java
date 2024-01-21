package com.totem.food.application.usecases.customer;

import com.totem.food.application.ports.in.mappers.customer.ICustomerMapper;
import com.totem.food.application.ports.out.persistence.commons.ISearchUniqueRepositoryPort;
import com.totem.food.application.ports.out.persistence.customer.CustomerModel;
import lombok.SneakyThrows;
import mock.models.CustomerModelMock;
import mock.ports.in.dto.CustomerDtoMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SearchUniqueCustomerUseCaseTest {

    @Spy
    private ICustomerMapper iCustomerMapper = Mappers.getMapper(ICustomerMapper.class);

    @Mock
    private ISearchUniqueRepositoryPort<Optional<CustomerModel>> uniqueRepositoryPort;

    private SearchUniqueCustomerUseCase searchUniqueCustomerUseCase;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        searchUniqueCustomerUseCase = new SearchUniqueCustomerUseCase(iCustomerMapper, uniqueRepositoryPort);
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        closeable.close();
    }

    @Test
    void item() {

        //## Mock - Objects
        var customerDto = CustomerDtoMock.getMock();
        var customerModel = CustomerModelMock.getMock();

        //## Given
        Mockito.when(uniqueRepositoryPort.findById(Mockito.anyString())).thenReturn(Optional.of(customerModel));

        //## When
        var result = searchUniqueCustomerUseCase.item(customerDto.getCpf());

        //## Then
        assertNotNull(result);
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(ZonedDateTime.class)
                .isEqualTo(customerDto);
    }
}