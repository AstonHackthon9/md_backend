package com.example.md_backend.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID roomID;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<User> users;

    @OneToOne
    private MetaData metaData;

    private LocalDateTime scheduledTime;

}
