package com.devtiro.tickets.controllers;

import com.devtiro.tickets.domain.dtos.GetTicketResponseDto;
import com.devtiro.tickets.domain.dtos.ListTicketResponseDto;
import com.devtiro.tickets.domain.entities.Ticket;
import com.devtiro.tickets.exceptions.TicketNotFoundException;
import com.devtiro.tickets.mappers.TicketMapper;
import com.devtiro.tickets.services.QrCodeService;
import com.devtiro.tickets.services.TicketService;
import com.devtiro.tickets.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;
import java.util.List;
import java.util.UUID;

import static com.devtiro.tickets.util.JwtUtil.parseUserId;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;
    private final QrCodeService qrCodeService;

    @GetMapping
    public ResponseEntity<Page<ListTicketResponseDto>> listTickets(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {
        Page<ListTicketResponseDto> listTicketResponseDtos = ticketService.listTicketsForUser(parseUserId(jwt), pageable)
                .map(ticketMapper::toListTicketResponseDto);

        return ResponseEntity.ok(listTicketResponseDtos);

    }


    @GetMapping("/{ticketId}")
    public ResponseEntity<GetTicketResponseDto> getTicket(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID ticketId) {
        return ticketService.getTicketForUser(parseUserId(jwt), ticketId)
                .map(ticketMapper::toGetTicketResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/{ticketId}/qr-codes")
    public ResponseEntity<byte[]> getQrCodeImage(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID ticketId) {

        byte[] qrCodeImage = qrCodeService.getQrCodeImageForUserAndTicket(parseUserId(jwt), ticketId);

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.IMAGE_PNG);
        httpHeaders.setContentLength(qrCodeImage.length);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(qrCodeImage);
    }
}
