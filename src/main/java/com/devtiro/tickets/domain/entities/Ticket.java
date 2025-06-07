package com.devtiro.tickets.domain.entities;

import com.devtiro.tickets.domain.enums.TicketStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tickets")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Ticket {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatusEnum status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaser_id")
    private User user;

    @OneToMany(mappedBy = "ticket",cascade = CascadeType.ALL)
    private List<QrCode> qrCodes = new ArrayList<>();

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<TicketValidation> validations = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

}
