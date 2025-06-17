package com.devtiro.tickets.domain.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketTypeRequestDto {

    @NotBlank(message = "Ticket type name is required.")
    private String name;

    @NotNull(message = "Ticket type price is required.")
    @PositiveOrZero(message = "Price must be zero or greater")
    private Double price;

    private String description;

    private Integer totalAvailable;
}
