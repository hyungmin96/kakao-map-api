package com.example.kakaomap.dto;

import com.example.kakaomap.entity.GroupBoardEntity;
import com.example.kakaomap.entity.GroupBoardFileEntity;
import com.example.kakaomap.entity.WriterExchangeEntity;
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

    // 게시글 DTO
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
    private List<GroupBoardFileEntity> files = new ArrayList<>();

    // 게시글 거래위치 DTO
    private String residence;
    private String longitude;
    private String latitude;
    private String location;
    private String preferTime;

    public void addFile(GroupBoardFileEntity file){
        files.add(file);
    }

    public GroupBoardEntity toEntity(WriterExchangeEntity writerExchangeEntity){
        return GroupBoardEntity.builder()
                .groupId(groupId)
                .content(content)
                .boardLike(0)
                .writerExchangeEntity(writerExchangeEntity)
                .BoardCategory(getCategory())
                .type(getType())
                .files(files)
                .build();
    }

    public WriterExchangeEntity getWriterExchangeEntity(){
        if(this.category.equals("exchange")){
            return WriterExchangeEntity.builder()
                    .residence(residence)
                    .longitude(longitude)
                    .location(location)
                    .latitude(latitude)
                    .exchangeTime(preferTime)
                    .build();
        } else return null;
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