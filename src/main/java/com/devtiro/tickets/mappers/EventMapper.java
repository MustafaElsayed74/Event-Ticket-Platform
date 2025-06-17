package com.devtiro.tickets.mappers;

import com.devtiro.tickets.domain.CreateEventRequest;
import com.devtiro.tickets.domain.CreateTicketTypeRequest;
import com.devtiro.tickets.domain.dtos.CreateEventRequestDto;
import com.devtiro.tickets.domain.dtos.CreateEventResponseDto;
import com.devtiro.tickets.domain.dtos.CreateTicketTypeRequestDto;
import com.devtiro.tickets.domain.dtos.CreateTicketTypeResponseDto;
import com.devtiro.tickets.domain.entities.Event;
import com.devtiro.tickets.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeResponseDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateTicketTypeRequestDto toDto(TicketType ticketType);

    CreateEventResponseDto toDto(Event event);

}
