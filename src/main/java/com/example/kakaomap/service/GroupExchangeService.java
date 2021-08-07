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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import static com.example.kakaomap.entity.QClientExchangeEntity.clientExchangeEntity;
import static com.example.kakaomap.entity.QWriterExchangeEntity.writerExchangeEntity;

@Service
@RequiredArgsConstructor
public class GroupExchangeService {

    private final GroupBoardRepository groupBoardRepository;
    private final GroupBoardFileRepository groupBoardFileRepository;
    private final ClientExchangeRepository clientExchangeRepository;
    private final ClientExchangeFileRepository clientExchangeFileRepository;
    private final WriterExchangeRepository writerExchangeRepository;
    private final JPAQueryFactory queryFactory;
    private final GenerateFile generateFile;


    public void selectRequest(ClientExchangeDTO clientExchangeDTO){
        WriterExchangeEntity writerExchangeEntity = writerExchangeRepository.getById(clientExchangeDTO.getBoardId());
        ClientExchangeEntity clientExchangeEntity = clientExchangeRepository.getById(clientExchangeDTO.getClientId());

        writerExchangeEntity.updateStatus(WriterExchangeEntity.status.process);
        writerExchangeEntity.selectExchangeEntity(clientExchangeEntity);
        writerExchangeRepository.save(writerExchangeEntity);
    }

    public JPAQuery<GroupExchangeApiController.ResponseClientDTO> getClientRequestList(GroupBoardDTO groupBoardDTO, Pageable page){
        return queryFactory
                .select(Projections.fields(GroupExchangeApiController.ResponseClientDTO.class,
                        writerExchangeEntity.exchange.id.as("processId"),
                        clientExchangeEntity.id.as("clientId"),
                        clientExchangeEntity.content.as("content"),
                        clientExchangeEntity.price.as("price"),
                        clientExchangeEntity.request.as("request"),
                        clientExchangeEntity.writerExchangeEntity.id.as("writerId"),
                        clientExchangeEntity.userId.as("userId")))
                .from(clientExchangeEntity)
                .where(writerExchangeEntity.id.eq(groupBoardDTO.getBoardId()))
                .offset(page.getPageNumber())
                .limit(page.getPageSize());
    }

    public ClientExchangeEntity clientPost(ClientExchangeDTO clientExchangeDTO){
        GroupBoardEntity writerExchangeEntity = groupBoardRepository.findExchageInfo(clientExchangeDTO.getBoardId());
        ClientExchangeEntity clientExchangeEntity = clientExchangeDTO.toEntity(writerExchangeEntity.getExchange());

        clientExchangeRepository.save(clientExchangeEntity);
        List<GenerateFileDTO> clientExchangeFiles = generateFile.createFile(clientExchangeDTO.getImages());
        clientExchangeFiles.stream().map(ExchagneFileDTO::new).collect(Collectors.toList())
                .forEach(item -> {
                    ExchangeFileEntity file = item.toEntity(clientExchangeEntity);
                    clientExchangeDTO.addFile(file);
                    clientExchangeFileRepository.save(file);
                });
        return clientExchangeEntity;
    }

    public GroupBoardEntity wrtierPost(GroupBoardDTO groupBoardDTO){
        GroupBoardEntity groupBoardEntity = groupBoardDTO.toEntity();
        groupBoardRepository.save(groupBoardEntity);

        List<GenerateFileDTO> groupBoardFiles = generateFile.createFile(groupBoardDTO.getBoard_img());
        groupBoardFiles.stream().map(GroupBoardFileDTO::new).collect(Collectors.toList())
                .forEach(item -> {
                    GroupBoardFileEntity file = item.toEntity(groupBoardEntity);
                    groupBoardDTO.addFile(file);
                    groupBoardFileRepository.save(file);
                });

        if(groupBoardDTO.getCategory().equals(GroupBoardEntity.BoardCategory.exchange))
            writerExchangeRepository.save(groupBoardDTO.getWriterExchangeEntity(groupBoardEntity));

        return groupBoardEntity;
    }

    public GroupBoardEntity getExchangeInfo(GroupBoardDTO groupBoardDTO){
        return groupBoardRepository.getExchangeInfo(groupBoardDTO.getBoardId());
    }

}
