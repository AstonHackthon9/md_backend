package com.example.md_backend.repository;

import com.example.md_backend.entity.MetaData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MetaDataRepo extends JpaRepository<MetaData, UUID> {
}
