package com.devtiro.tickets.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;


    @OneToMany(mappedBy = "organizer",cascade = CascadeType.ALL)
    private List<Event> organizedEvents = new ArrayList<>();

    @ManyToMany
    @JoinTable(
          name ="user_attending_events",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> attendingEvents = new ArrayList<>();

   @ManyToMany
   @JoinTable(
           name = "user_staffing_events",
           joinColumns = @JoinColumn(name = "user_id"),
           inverseJoinColumns = @JoinColumn(name = "event_id")
   )
    private List<Event> staffingEvents = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDate createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDate updatedAt;
}
