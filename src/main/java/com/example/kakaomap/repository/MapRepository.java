package com.example.kakaomap.repository;

import com.example.kakaomap.entity.WriterExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapRepository extends JpaRepository<WriterExchangeEntity, Long> {

}
