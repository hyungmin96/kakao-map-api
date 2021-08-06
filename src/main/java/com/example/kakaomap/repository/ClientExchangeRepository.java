package com.example.kakaomap.repository;

import com.example.kakaomap.entity.ClientExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientExchangeRepository extends JpaRepository<ClientExchangeEntity, Long> {
}
