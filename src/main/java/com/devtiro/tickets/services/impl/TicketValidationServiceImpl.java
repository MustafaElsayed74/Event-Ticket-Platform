package com.devtiro.tickets.services.impl;

import com.devtiro.tickets.domain.entities.QrCode;
import com.devtiro.tickets.domain.entities.Ticket;
import com.devtiro.tickets.domain.entities.TicketValidation;
import com.devtiro.tickets.domain.enums.QrCodeStatusEnum;
import com.devtiro.tickets.domain.enums.TicketValidationMethodEnum;
import com.devtiro.tickets.domain.enums.TicketValidationStatusEnum;
import com.devtiro.tickets.exceptions.QrCodeNotFoundException;
import com.devtiro.tickets.exceptions.TicketNotFoundException;
import com.devtiro.tickets.repositories.QrCodeRepository;
import com.devtiro.tickets.repositories.TicketRepository;
import com.devtiro.tickets.repositories.TicketValidationRepository;
import com.devtiro.tickets.services.TicketValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketValidationServiceImpl implements TicketValidationService {

    private final TicketValidationRepository ticketValidationRepository;
    private final QrCodeRepository qrCodeRepository;
    private final TicketRepository ticketRepository;

    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {

        QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatusEnum.ACTIVE)
                .orElseThrow(() -> new QrCodeNotFoundException(
                        String.format(
                                "Qr Code with ID %s was not found.", qrCodeId
                        )
                ));

        Ticket ticket = qrCode.getTicket();

        TicketValidation ticketValidation = new TicketValidation();

        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(TicketValidationMethodEnum.QR_SCAN);

        TicketValidationStatusEnum status = ticket.getValidations().stream()
                .filter(v -> TicketValidationStatusEnum.VALID.equals(v.getStatus()))
                .findFirst()
                .map(v -> TicketValidationStatusEnum.INVALID)
                .orElse(TicketValidationStatusEnum.VALID);

        ticketValidation.setStatus(status);

        return ticketValidationRepository.save(ticketValidation);

    }

    @Override
    public TicketValidation validateTicketManually(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(
                        String.format(
                                "Ticket By ID %s Was Not Found", ticketId
                        )
                ));

        TicketValidation ticketValidation = new TicketValidation();

        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(TicketValidationMethodEnum.MANUAL);

        TicketValidationStatusEnum status = ticket.getValidations().stream()
                .filter(v -> TicketValidationStatusEnum.VALID.equals(v.getStatus()))
                .findFirst()
                .map(v -> TicketValidationStatusEnum.INVALID)
                .orElse(TicketValidationStatusEnum.VALID);

        ticketValidation.setStatus(status);

        return ticketValidationRepository.save(ticketValidation);
    }
}
