package com.devtiro.tickets.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;

    // TODO: Organized events

    // TODO: Attending events

    // TODO: Staffing events
    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDate createdAt;
    @LastModifiedDate
    @Column(updatable = true,nullable = false)
    private LocalDate updatedAt;
}
