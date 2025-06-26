package com.devtiro.tickets.controllers;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventMapper mapper;
    private final EventService eventService;


    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDto>> listEvents(Pageable pageable) {
        return ResponseEntity.ok(eventService.listPublishedEvents(pageable)
                .map(mapper::toListPublishedEventResponseDto));
    }

}
