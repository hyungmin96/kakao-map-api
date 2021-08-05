package com.example.kakaomap.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class
GroupBoardDTO implements Serializable {

    private int page;
    private int display;

    private Long groupId;
    private Long boardId;
    private String type = "general";
    private String category = "article";
    private Long userId;
    private String username;
    private String content;
    private String result;
    private int[] deleteImageIndex;
    private LocalDateTime regTime;
    private int boardLike = 0;
    private MultipartFile[] board_img;
    private WriterExchangeEntity writerExchangeEntity;
    private List<GroupBoardFileEntity> files = new ArrayList<>();

    public void addFile(GroupBoardFileEntity file){
        files.add(file);
    }

    public GroupBoardEntity toEntity(){
        return GroupBoardEntity.builder()
                .groupId(groupId)
                .content(content)
                .exchange(writerExchangeEntity)
                .boardLike(0)
                .BoardCategory(getCategory())
                .type(getType())
                .files(files)
                .build();
    }

    public GroupBoardEntity.BoardType getType(){
        if(this.type.equals("notice"))
            return GroupBoardEntity.BoardType.notice;
        else if(this.type.equals("fixed"))
            return GroupBoardEntity.BoardType.fix;
        else
            return GroupBoardEntity.BoardType.general;
    }

    public GroupBoardEntity.BoardCategory getCategory(){
        if(this.category.equals("article"))
            return GroupBoardEntity.BoardCategory.article;
        else if(this.category.equals("exchange"))
            return GroupBoardEntity.BoardCategory.exchange;
        else
            return GroupBoardEntity.BoardCategory.complete;
    }

}