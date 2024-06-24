package com.inventorymanager.service.order;

import com.inventorymanager.domain.order.OrderItem;
import com.inventorymanager.service.order.Dtos.OrderItemCreateDto;
import com.inventorymanager.service.order.Dtos.OrderItemReadDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD, uses = OrderItemMapperHelper.class)
public interface OrderItemMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "stock", source = "stockId")
    OrderItem toOrderItem(OrderItemCreateDto orderItemCreateDto);

    @Mapping(target = "stockId", source = "stock.id")
    OrderItemReadDto toOrderItemReadDto(OrderItem orderItem);
}

