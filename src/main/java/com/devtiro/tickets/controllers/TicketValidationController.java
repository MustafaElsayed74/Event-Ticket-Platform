package com.devtiro.tickets.controllers;

import com.devtiro.tickets.domain.TicketValidationRequest;
import com.devtiro.tickets.domain.dtos.TicketValidationResponseDto;
import com.devtiro.tickets.domain.entities.TicketValidation;
import com.devtiro.tickets.domain.enums.TicketValidationMethodEnum;
import com.devtiro.tickets.mappers.TicketValidationMapper;
import com.devtiro.tickets.services.TicketValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ticket-validations")
public class TicketValidationController {

    private final TicketValidationService ticketValidationService;
    private final TicketValidationMapper mapper;

    @PostMapping
    public ResponseEntity<TicketValidationResponseDto> validateTicket( @RequestBody TicketValidationRequest ticketValidationRequest) {
        TicketValidationMethodEnum method = ticketValidationRequest.getMethod();

        TicketValidation ticketValidation;
        if (TicketValidationMethodEnum.MANUAL.equals(method)) {
            ticketValidation = ticketValidationService.
                    validateTicketManually(ticketValidationRequest.getId());
        } else {
            ticketValidation = ticketValidationService.
                    validateTicketByQrCode(ticketValidationRequest.getId());
        }

        return ResponseEntity.ok(mapper.toDto(ticketValidation));

    }

}
