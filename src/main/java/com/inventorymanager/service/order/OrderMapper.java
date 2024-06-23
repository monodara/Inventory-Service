package com.inventorymanager.service.order;

import com.inventorymanager.domain.order.Order;
import com.inventorymanager.service.order.Dtos.OrderCreateDto;
import com.inventorymanager.service.order.Dtos.OrderReadDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "orderItems", source = "orderCreateDto.orderItems")
    Order toOrder(OrderCreateDto orderCreateDto);

    @Mapping(target = "orderItems", source = "orderItems")
    OrderReadDto ReadOrder(Order order);
}

