package com.example.kakaomap.repository;

import com.example.kakaomap.entity.ClientExchangeEntity;
import com.example.kakaomap.entity.GroupBoardEntity;
import com.example.kakaomap.entity.WriterExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupBoardRepository extends JpaRepository<GroupBoardEntity, Long> {


}
