package com.devtiro.tickets.controllers;

import com.devtiro.tickets.domain.dtos.ListTicketResponseDto;
import com.devtiro.tickets.domain.entities.Ticket;
import com.devtiro.tickets.mappers.TicketMapper;
import com.devtiro.tickets.services.TicketService;
import com.devtiro.tickets.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.devtiro.tickets.util.JwtUtil.parseUserId;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @GetMapping
    public ResponseEntity<Page<ListTicketResponseDto>> listTickets(@AuthenticationPrincipal Jwt jwt, Pageable pageable){
        Page<ListTicketResponseDto> listTicketResponseDtos = ticketService.listTicketsForUser(parseUserId(jwt), pageable)
                .map(ticketMapper::toListTicketResponseDto);

        return ResponseEntity.ok(listTicketResponseDtos);

    }

}
