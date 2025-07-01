package com.devtiro.tickets.controllers;

import com.devtiro.tickets.repositories.TicketRepository;
import com.devtiro.tickets.repositories.TicketTypeRepository;
import com.devtiro.tickets.services.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.devtiro.tickets.util.JwtUtil.parseUserId;

@RestController
@RequestMapping("/api/v1/events/{event_id}/ticket-types")
@RequiredArgsConstructor
public class TicketTypeController {

    private final TicketTypeService ticketTypeService;

    @PostMapping("/{ticketTypeId}/tickets")
    public ResponseEntity<Void> purchasedTicket(@AuthenticationPrincipal Jwt jwt,@PathVariable UUID ticketTypeId) {
        UUID userId = parseUserId(jwt);
        ticketTypeService.purchaseTicket(userId,ticketTypeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
