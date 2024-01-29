package com.totem.food.application.usecases.customer;

import com.totem.food.application.ports.in.dtos.customer.CustomerDto;
import com.totem.food.application.ports.in.mappers.customer.ICustomerMapper;
import com.totem.food.application.ports.out.persistence.commons.ISearchUniqueRepositoryPort;
import com.totem.food.application.ports.out.persistence.customer.CustomerModel;
import com.totem.food.application.usecases.annotations.UseCase;
import com.totem.food.application.usecases.commons.ISearchUniqueUseCase;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
@UseCase
public class SearchUniqueCustomerUseCase implements ISearchUniqueUseCase<String, CustomerDto> {

    private final ICustomerMapper iCustomerMapper;
    private final ISearchUniqueRepositoryPort<Optional<CustomerModel>> uniqueRepositoryPort;

    @Override
    public CustomerDto item(String cpf) {
        return uniqueRepositoryPort.findById(cpf).map(iCustomerMapper::toDto)
                .orElseThrow();
    }
}
