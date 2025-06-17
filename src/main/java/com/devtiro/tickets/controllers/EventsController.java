package com.devtiro.tickets.controllers;

import com.devtiro.tickets.domain.CreateEventRequest;
import com.devtiro.tickets.domain.dtos.CreateEventRequestDto;
import com.devtiro.tickets.domain.dtos.CreateEventResponseDto;
import com.devtiro.tickets.domain.entities.Event;
import com.devtiro.tickets.mappers.EventMapper;
import com.devtiro.tickets.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventsController {
    private final EventService eventService;
    private final EventMapper mapper;

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody CreateEventRequestDto createEventRequestDto) {

        CreateEventRequest eventRequest = mapper.fromDto(createEventRequestDto);
        UUID userId = UUID.fromString(jwt.getSubject());
        Event CreatedEvent = eventService.createEvent(userId, eventRequest);
        CreateEventResponseDto createEventResponseDto  = mapper.toDto(CreatedEvent);

        return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);

    }
}
