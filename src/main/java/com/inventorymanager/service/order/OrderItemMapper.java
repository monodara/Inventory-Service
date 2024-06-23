package com.inventorymanager.service.order;

import com.inventorymanager.domain.order.OrderItem;
import com.inventorymanager.service.order.Dtos.OrderItemCreateDto;
import com.inventorymanager.service.order.Dtos.OrderItemReadDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem toOrderItem(OrderItemCreateDto orderItemCreateDto);

    @Mapping(target = "stockId", source = "stock.id")
    OrderItemReadDto readOrderItem(OrderItem orderItem);
}

