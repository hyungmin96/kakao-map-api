package com.example.kakaomap.service;

import com.example.kakaomap.component.GenerateFile;
import com.example.kakaomap.dto.*;
import com.example.kakaomap.entity.*;
import com.example.kakaomap.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupExchangeService {

    private final GroupBoardRepository groupBoardRepository;
    private final GroupBoardFileRepository groupBoardFileRepository;
    private final ClientExchangeRepository clientExchangeRepository;
    private final ClientExchangeFileRepository clientExchangeFileRepository;
    private final WriterExchangeRepository writerExchangeRepository;
    private final GenerateFile generateFile;


    public List<ClientExchangeEntity> getClientRequestList(GroupBoardDTO groupBoardDTO){
        GroupBoardEntity groupBoard = groupBoardRepository.getById(groupBoardDTO.getBoardId());
        return writerExchangeRepository.findAllRequestList(groupBoard);
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

    public List<GroupBoardEntity> getExchangeBoards(GroupBoardDTO groupBoardDTO){
        return groupBoardRepository.getExchangeBoards(groupBoardDTO.getGroupId());
    }

}
