package com.devtiro.tickets.controllers;

import com.devtiro.tickets.domain.dtos.GetPublishedEventDetailsResponseDto;
import com.devtiro.tickets.domain.dtos.ListEventResponseDto;
import com.devtiro.tickets.domain.dtos.ListPublishedEventResponseDto;
import com.devtiro.tickets.domain.entities.Event;
import com.devtiro.tickets.mappers.EventMapper;
import com.devtiro.tickets.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventMapper mapper;
    private final EventService eventService;


    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDto>> listEvents(@RequestParam(required = false) String q, Pageable pageable) {

        Page<Event> events = (null != q && !q.trim().isEmpty())
                ? eventService.searchPublishEvents(q, pageable)
                : eventService.listPublishedEvents(pageable);

        return ResponseEntity.ok(events.map(mapper::toListPublishedEventResponseDto));
    }

    @GetMapping("/{event_id}")
    public ResponseEntity<GetPublishedEventDetailsResponseDto> getPublishedEvent(@PathVariable UUID event_id){

         return eventService.getPublishedEvent(event_id)
                 .map(mapper::toGetPublishedEventDetailsResponseDto)
                 .map(ResponseEntity::ok)
                 .orElse(ResponseEntity.notFound().build());
    }

}
