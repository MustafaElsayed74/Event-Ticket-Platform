package com.devtiro.tickets.controllers;

import com.devtiro.tickets.domain.CreateEventRequest;
import com.devtiro.tickets.domain.dtos.*;
import com.devtiro.tickets.domain.entities.Event;
import com.devtiro.tickets.mappers.EventMapper;
import com.devtiro.tickets.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

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
        UUID userId = getUserId(jwt);
        Event CreatedEvent = eventService.createEvent(userId, eventRequest);
        CreateEventResponseDto createEventResponseDto = mapper.toDto(CreatedEvent);

        return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<Page<ListEventResponseDto>> listEvents(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {
        UUID userId = getUserId(jwt);
        Page<Event> events = eventService.listEventsForOrganizer(userId, pageable);

        return ResponseEntity.ok(events.map(mapper::toListEventResponseDto));
    }


    @GetMapping("/{event_id}")
    public ResponseEntity<GetEventDetailsResponseDto> getEventForOrganizer(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID event_id) {
        UUID userId = getUserId(jwt);
        return eventService.getEventForOrganizer(userId, event_id)
                .map(mapper::toGetEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{event_id}")
    public ResponseEntity<UpdateEventResponseDto> updateEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID event_id,
           @Valid @RequestBody UpdateEventRequestDto updateEventRequestDto
    ) {

        UUID userId = getUserId(jwt);
        Event updatedEvent = eventService.updateEventForOrganizer(userId, event_id, mapper.fromDto(updateEventRequestDto));
        return ResponseEntity.ok(mapper.toUpdateEventResponseDto(updatedEvent));

    }

    @DeleteMapping("/{event_id}")
    public ResponseEntity<HttpStatus> deleteEvent(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID event_id){

        UUID userId = getUserId(jwt);
        eventService.deleteEventForOrganizer(userId,event_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    private UUID getUserId(Jwt jwt) {
        return UUID.fromString(jwt.getSubject());
    }

}
