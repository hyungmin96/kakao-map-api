package com.example.kakaomap.dto;

import com.example.kakaomap.entity.GroupBoardEntity;
import com.example.kakaomap.entity.GroupBoardFileEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GroupBoardFileDTO {

    private String name;
    private String path;
    private Long groupId;
    private int size;

    public GroupBoardFileDTO(GenerateFileDTO groupFileDTO) {
        this.name = groupFileDTO.getName();
        this.path = groupFileDTO.getPath();
    }

    public GroupBoardFileEntity toEntity(GroupBoardEntity board){
        return GroupBoardFileEntity.builder()
                .name(name)
                .path(path)
                .groupBoard(board)
                .groupId(board.getGroupId())
                .build();
    }
}