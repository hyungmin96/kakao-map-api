package com.example.kakaomap.dto;

import com.example.kakaomap.entity.ClientExchangeEntity;
import com.example.kakaomap.entity.ExchangeFileEntity;
import com.example.kakaomap.entity.GroupBoardEntity;
import com.example.kakaomap.entity.GroupBoardFileEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExchagneFileDTO {
    private String name;
    private String path;
    private Long groupId;
    private int size;

    public ExchagneFileDTO(GenerateFileDTO groupFileDTO) {
        this.name = groupFileDTO.getName();
        this.path = groupFileDTO.getPath();
    }

    public ExchangeFileEntity toEntity(ClientExchangeEntity entity){
        return ExchangeFileEntity.builder()
                .name(name)
                .path(path)
                .client(entity)
                .build();
    }
}
