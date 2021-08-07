package com.example.kakaomap.service;

import com.example.kakaomap.api.GroupExchangeApiController;
import com.example.kakaomap.component.GenerateFile;
import com.example.kakaomap.dto.*;
import com.example.kakaomap.entity.*;
import com.example.kakaomap.repository.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.kakaomap.entity.QGroupBoardEntity.groupBoardEntity;
import static com.example.kakaomap.entity.QWriterClientJoinEntity.writerClientJoinEntity;

@Service
@RequiredArgsConstructor
public class GroupExchangeService {

    private final GroupBoardRepository groupBoardRepository;
    private final GroupBoardFileRepository groupBoardFileRepository;
    private final ClientExchangeRepository clientExchangeRepository;
    private final ClientExchangeFileRepository clientExchangeFileRepository;
    private final WriterExchangeRepository writerExchangeRepository;
    private final WriterClientJoinRepository writerClientJoinRepository;
    private final JPAQueryFactory queryFactory;
    private final GenerateFile generateFile;

    // * writer와 client의 교환이 진행중이지 않다면 교환요청
    // * request to exchange user from client api
    @Transactional
    public GroupExchangeApiController.ResponseRequestExchangeDTO selectRequest(ClientExchangeDTO clientExchangeDTO){
        // search to check if an exchange with another entity is already in progress
        Integer isProgressEntity = queryFactory
                .selectOne()
                .from(writerClientJoinEntity)
                .where(writerClientJoinEntity.writerExchangeEntity.id.eq(clientExchangeDTO.getBoardId())
                .and(writerClientJoinEntity.status.eq(WriterClientJoinEntity.status.process)))
                .fetchFirst();
        // if isProgress value is 'null', request exchange to writer entity
        if (isProgressEntity == null) {
            WriterClientJoinEntity writerClientJoinEntity = queryFactory
                    .selectFrom(QWriterClientJoinEntity.writerClientJoinEntity)
                    .join(QWriterClientJoinEntity.writerClientJoinEntity.writerExchangeEntity)
                    .fetchJoin()
                    .join(QWriterClientJoinEntity.writerClientJoinEntity.clientExchangeEntity)
                    .fetchJoin()
                    .where(QWriterClientJoinEntity.writerClientJoinEntity.writerExchangeEntity.id.eq(clientExchangeDTO.getBoardId())
                    .and(QWriterClientJoinEntity.writerClientJoinEntity.clientExchangeEntity.id.eq(clientExchangeDTO.getClientId())))
                    .fetchOne();
            // update exchange status
            writerClientJoinEntity.updateStatus(WriterClientJoinEntity.status.process);
            writerClientJoinRepository.save(writerClientJoinEntity);
            return new GroupExchangeApiController.ResponseRequestExchangeDTO(writerClientJoinEntity);
        }
        return null;
    }

    // client가 해당 게시글에 교환 요청한 entity를 조회
    //  To check the list of exchange reuqest entities on the client
    public JPAQuery<GroupExchangeApiController.ResponseClientDTO> getClientRequestList(GroupBoardDTO groupBoardDTO, Pageable page){
        return queryFactory
                .select(Projections.fields(GroupExchangeApiController.ResponseClientDTO.class,
                        writerClientJoinEntity.clientExchangeEntity.id.as("clientId"),
                        writerClientJoinEntity.clientExchangeEntity.content.as("content"),
                        writerClientJoinEntity.clientExchangeEntity.price.as("price"),
                        writerClientJoinEntity.clientExchangeEntity.request.as("request"),
                        writerClientJoinEntity.status.as("status"),
                        writerClientJoinEntity.clientExchangeEntity.userId.as("userId")))
                .from(writerClientJoinEntity)
                .where(writerClientJoinEntity.clientExchangeEntity.boardId.eq(groupBoardDTO.getBoardId()))
                .offset(page.getPageNumber())
                .limit(page.getPageSize());
    }

    // client가 writer 게시글에 교환을 요청
    public ClientExchangeEntity clientPost(ClientExchangeDTO clientExchangeDTO){
        WriterExchangeEntity writerExchangeEntity = writerExchangeRepository.findWriterExchangeEntityByboardId(clientExchangeDTO.getBoardId());
        ClientExchangeEntity clientExchangeEntity = clientExchangeDTO.toEntity();
        clientExchangeRepository.save(clientExchangeEntity);
        WrtierClientJoinDTO writerClientJoinEntity = new WrtierClientJoinDTO(writerExchangeEntity, clientExchangeEntity);
        List<GenerateFileDTO> clientExchangeFiles = generateFile.createFile(clientExchangeDTO.getImages());
        clientExchangeFiles.stream().map(ExchagneFileDTO::new).collect(Collectors.toList())
                .forEach(item -> {
                    ExchangeFileEntity file = item.toEntity(clientExchangeEntity);
                    clientExchangeDTO.addFile(file);
                    clientExchangeFileRepository.save(file);
                });
        writerClientJoinRepository.save(writerClientJoinEntity.toEntity());
        return clientExchangeEntity;
    }

    public GroupBoardEntity wrtierPost(GroupBoardDTO groupBoardDTO){
        WriterExchangeEntity writerExchangeEntity = groupBoardDTO.getWriterExchangeEntity();
        GroupBoardEntity groupBoardEntity = groupBoardDTO.toEntity(writerExchangeEntity);
        groupBoardRepository.save(groupBoardEntity);
        List<GenerateFileDTO> groupBoardFiles = generateFile.createFile(groupBoardDTO.getBoard_img());
        groupBoardFiles.stream().map(GroupBoardFileDTO::new).collect(Collectors.toList())
                .forEach(item -> {
                    GroupBoardFileEntity file = item.toEntity(groupBoardEntity);
                    groupBoardDTO.addFile(file);
                    groupBoardFileRepository.save(file);
                });
        return groupBoardEntity;
    }

    public GroupBoardEntity getExchangeInfo(GroupBoardDTO groupBoardDTO){
        return queryFactory
                .selectFrom(groupBoardEntity)
                .innerJoin(groupBoardEntity.writerExchangeEntity)
                .fetchJoin()
                .where(groupBoardEntity.boardId.eq(groupBoardDTO.getBoardId()))
                .fetchOne();
    }

}
