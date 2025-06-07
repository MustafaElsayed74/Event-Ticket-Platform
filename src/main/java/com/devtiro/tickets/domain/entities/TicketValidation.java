package com.devtiro.tickets.domain.entities;

import com.devtiro.tickets.domain.enums.TicketValidationMethodEnum;
import com.devtiro.tickets.domain.enums.TicketValidationStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ticket_validations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TicketValidation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketValidationStatusEnum status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketValidationMethodEnum validationMethod;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;


}
