package com.example.md_backend.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "metadata")
public class MetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID meta_id;

    @NotNull
    private String jobRole;

    @NotNull
    private String jobDesc;


    private byte[] pdf;
}
