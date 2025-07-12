package com.devtiro.tickets.domain;

import com.devtiro.tickets.domain.entities.TicketValidation;
import com.devtiro.tickets.domain.enums.TicketValidationMethodEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationRequest {
    private UUID id;
    private TicketValidationMethodEnum method;
}
