package com.inventorymanager.controller.shared;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorEntity {
    private String field;
    private String message;
}
