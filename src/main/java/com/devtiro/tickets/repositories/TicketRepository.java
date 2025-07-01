package com.devtiro.tickets.repositories;

import com.devtiro.tickets.domain.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketRepository  extends JpaRepository<Ticket, UUID> {
    int countByTicketTypeId(UUID ticketTypeId);
}
