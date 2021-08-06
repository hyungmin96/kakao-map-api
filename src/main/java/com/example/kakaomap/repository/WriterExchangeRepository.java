package com.example.kakaomap.repository;

import com.example.kakaomap.entity.ClientExchangeEntity;
import com.example.kakaomap.entity.GroupBoardEntity;
import com.example.kakaomap.entity.WriterExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WriterExchangeRepository extends JpaRepository<WriterExchangeEntity, Long> {
    @Query(value = "select w.clients from WriterExchangeEntity w where w.groupBoard = :groupBoard")
    List<ClientExchangeEntity> findAllRequestList(GroupBoardEntity groupBoard);
}
