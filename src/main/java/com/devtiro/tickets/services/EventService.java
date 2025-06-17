package com.devtiro.tickets.services;

import com.devtiro.tickets.domain.dtos.CreateEventRequest;
import com.devtiro.tickets.domain.entities.Event;

import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest event);
}
