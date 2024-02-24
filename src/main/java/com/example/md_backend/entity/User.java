package com.example.md_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.UUID;


@Entity
@Data
@Table(name = "user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String role;

    private String phoneNumber;


}
