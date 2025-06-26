package com.devtiro.tickets.services.impl;

import com.devtiro.tickets.domain.CreateEventRequest;
import com.devtiro.tickets.domain.UpdateEventRequest;
import com.devtiro.tickets.domain.UpdateTicketTypeRequest;
import com.devtiro.tickets.domain.entities.Event;
import com.devtiro.tickets.domain.entities.TicketType;
import com.devtiro.tickets.domain.entities.User;
import com.devtiro.tickets.exceptions.EventNotFoundException;
import com.devtiro.tickets.exceptions.EventUpdateException;
import com.devtiro.tickets.exceptions.TicketTypeNotFoundException;
import com.devtiro.tickets.exceptions.UserNotFoundException;
import com.devtiro.tickets.repositories.EventRepository;
import com.devtiro.tickets.repositories.UserRepository;
import com.devtiro.tickets.services.EventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public Event createEvent(UUID organizerId, CreateEventRequest event) {

        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with ID '%s' not found", organizerId)));

        Event eventToCreate = new Event();


        List<TicketType> ticketTypesToCreate = event.getTicketTypes().stream().map(ticketType -> {
            TicketType ticketTypeToCreate = new TicketType();

            ticketTypeToCreate.setName(ticketType.getName());
            ticketTypeToCreate.setDescription(ticketType.getDescription());
            ticketTypeToCreate.setPrice(ticketType.getPrice());
            ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
            ticketTypeToCreate.setEvent(eventToCreate);
            return ticketTypeToCreate;
        }).toList();

        eventToCreate.setName(event.getName());
        eventToCreate.setStart(event.getStart());
        eventToCreate.setEnd(event.getEnd());
        eventToCreate.setVenue(event.getVenue());
        eventToCreate.setSalesStart(event.getSalesStart());
        eventToCreate.setSalesEnd(event.getSalesEnd());
        eventToCreate.setStatus(event.getStatus());
        eventToCreate.setOrganizer(organizer);
        eventToCreate.setTicketTypes(ticketTypesToCreate);


        return eventRepository.save(eventToCreate);

    }

    @Override
    public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable) {
        return eventRepository.findByOrganizerId(organizerId, pageable);


    }

    @Override
    public Optional<Event> getEventForOrganizer(UUID organizerId, UUID id) {
        return eventRepository.findByIdAndOrganizerId(id, organizerId);
    }

    @Override
    @Transactional
    public Event updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequest event) {

        if (null == event.getId())
            throw new EventUpdateException("Event ID cannot be null");
        if (!id.equals(event.getId()))
            throw new EventUpdateException("Cannot update event ID");

        Event existingEvent = eventRepository.findByIdAndOrganizerId(id, organizerId)
                .orElseThrow(() -> new EventNotFoundException(
                        String.format("Event with ID '%s' not found.", id)));

        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with '%s' not found.", organizerId)));

        if (!existingEvent.getOrganizer().getId().equals(organizerId))
            throw new EventUpdateException("You are not authorized to update this event");


        existingEvent.setName(event.getName());
        existingEvent.setVenue(event.getVenue());
        existingEvent.setSalesStart(event.getSalesStart());
        existingEvent.setSalesEnd(event.getSalesEnd());
        existingEvent.setStart(event.getStart());
        existingEvent.setEnd(event.getEnd());
        existingEvent.setStatus(event.getStatus());

        Set<UUID> requestTicketTypeIds = event.getTicketTypes()
                .stream()
                .map(UpdateTicketTypeRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        existingEvent.getTicketTypes().removeIf(existingTicketType ->
                !requestTicketTypeIds.contains(existingTicketType.getId())
        );

        Map<UUID, TicketType> existingTicketTypesIndex = existingEvent.getTicketTypes()
                .stream()
                .collect(Collectors.toMap(TicketType::getId, Function.identity()));

        for (UpdateTicketTypeRequest ticketType : event.getTicketTypes()) {
            if (null == ticketType.getId()) {
                //Create
                TicketType ticketTypeToCreate = new TicketType();

                ticketTypeToCreate.setName(ticketType.getName());
                ticketTypeToCreate.setDescription(ticketType.getDescription());
                ticketTypeToCreate.setPrice(ticketType.getPrice());
                ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                existingEvent.getTicketTypes().add(ticketTypeToCreate);
                ticketTypeToCreate.setEvent(existingEvent);
            } else if (existingTicketTypesIndex.containsKey(ticketType.getId())) {
                //Update
                TicketType existingTicketType = existingTicketTypesIndex.get(ticketType.getId());
                existingTicketType.setName(ticketType.getName());
                existingTicketType.setDescription(ticketType.getDescription());
                existingTicketType.setPrice(ticketType.getPrice());
                existingTicketType.setTotalAvailable(ticketType.getTotalAvailable());

            } else
                throw new TicketTypeNotFoundException(
                        String.format("Ticket type with ID '%s' does not exist.", ticketType.getId())
                );

        }


        return eventRepository.save(existingEvent);
    }

    @Override
    @Transactional
    public void deleteEventForOrganizer(UUID organizerId, UUID id) {
      getEventForOrganizer(organizerId,id).ifPresent(eventRepository::delete);
    }
}
