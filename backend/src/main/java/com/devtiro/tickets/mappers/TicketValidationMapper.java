package com.devtiro.tickets.mappers;

import com.devtiro.tickets.domain.dtos.TicketValidationResponseDto;
import com.devtiro.tickets.domain.entities.TicketValidation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketValidationMapper {

    @Mapping(target = "ticketId",source = "ticket.id")
    TicketValidationResponseDto toDto(TicketValidation ticketValidation);

}
