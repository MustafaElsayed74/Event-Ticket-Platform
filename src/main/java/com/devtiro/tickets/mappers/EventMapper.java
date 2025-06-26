package com.devtiro.tickets.mappers;

import com.devtiro.tickets.domain.CreateEventRequest;
import com.devtiro.tickets.domain.CreateTicketTypeRequest;
import com.devtiro.tickets.domain.UpdateEventRequest;
import com.devtiro.tickets.domain.UpdateTicketTypeRequest;
import com.devtiro.tickets.domain.dtos.*;
import com.devtiro.tickets.domain.entities.Event;
import com.devtiro.tickets.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeResponseDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);

    ListTicketTypeResponseDto toDto(TicketType ticketType);

    ListEventResponseDto toListEventResponseDto(Event event);

    GetEventDetailsResponseDto toGetEventDetailsResponseDto(Event event);

    GetTicketTypeDetailsResponseDto toGetTicketTypeDetailsResponseDto(TicketType ticketType);

    UpdateTicketTypeRequest fromDto(UpdateTicketTypeRequestDto dto);

    UpdateEventRequest fromDto(UpdateEventRequestDto dto);

    UpdateTicketTypeResponseDto toUpdateTicketTypeResponseDto(TicketType ticketType);

    UpdateEventResponseDto toUpdateEventResponseDto(Event event);

    ListPublishedEventResponseDto toListPublishedEventResponseDto(Event event);
}
