package com.example.kakaomap.repository;

import com.example.kakaomap.entity.ExchangeFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientExchangeFileRepository extends JpaRepository<ExchangeFileEntity, Long> {
}
