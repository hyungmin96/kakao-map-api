package com.example.kakaomap.repository;

import com.example.kakaomap.entity.GroupBoardEntity;
import com.example.kakaomap.entity.WriterExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupBoardRepository extends JpaRepository<GroupBoardEntity, Long> {

    @Query(value = "select b from GroupBoardEntity b " +
            "join fetch b.exchange e " +
            "where b.groupId = :groupId and b.BoardCategory ='exchange'")
    List<GroupBoardEntity> getExchangeBoards(Long groupId);

    @Query(value = "select b from GroupBoardEntity b " +
            "join fetch b.exchange e " +
            "join fetch b.files f " +
            "where b.boardId = :boardId")
    GroupBoardEntity getExchangeInfo(Long boardId);

    @Query(value = "select w from GroupBoardEntity w join fetch w.exchange where w.boardId = :boardId")
    GroupBoardEntity findExchageInfo(long boardId);

}
