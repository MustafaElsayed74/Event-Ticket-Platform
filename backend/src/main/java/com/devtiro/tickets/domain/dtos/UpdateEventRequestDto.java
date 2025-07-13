package com.devtiro.tickets.domain.dtos;

import com.devtiro.tickets.domain.enums.EventStatusEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequestDto {

    @NotNull(message = "Event ID cannot be null.")
    private UUID id;
    @NotBlank(message = "Event name is required.")
    private String name;

    private LocalDateTime start;

    private LocalDateTime end;

    @NotBlank(message = "Venue information is required,")
    private String venue;

    private LocalDateTime salesStart;

    private LocalDateTime salesEnd;

    @NotNull(message = "Event status must be provided.")
    private EventStatusEnum status;

    @NotEmpty(message = "At least one ticket type must be provided.")
    @Valid
    private List<UpdateTicketTypeRequestDto> ticketTypes;
}
