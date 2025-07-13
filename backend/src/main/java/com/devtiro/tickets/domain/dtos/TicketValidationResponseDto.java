package com.devtiro.tickets.domain.dtos;

import com.devtiro.tickets.domain.enums.TicketValidationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationResponseDto {

    private UUID ticketId;
    private TicketValidationStatusEnum status;
}
