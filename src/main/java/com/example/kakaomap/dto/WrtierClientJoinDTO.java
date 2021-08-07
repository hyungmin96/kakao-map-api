package com.example.kakaomap.dto;

import com.example.kakaomap.entity.ClientExchangeEntity;
import com.example.kakaomap.entity.WriterClientJoinEntity;
import com.example.kakaomap.entity.WriterExchangeEntity;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class WrtierClientJoinDTO {

    private WriterExchangeEntity writerExchangeEntity;
    private ClientExchangeEntity clientExchangeEntity;
    private WriterClientJoinEntity.status status = WriterClientJoinEntity.status.wait;

    public WrtierClientJoinDTO(WriterExchangeEntity writerExchangeEntity, ClientExchangeEntity clientExchangeEntity) {
        this.writerExchangeEntity = writerExchangeEntity;
        this.clientExchangeEntity = clientExchangeEntity;
    }

    public WriterClientJoinEntity toEntity(){
        return WriterClientJoinEntity.builder()
                .writerExchangeEntity(writerExchangeEntity)
                .clientExchangeEntity(clientExchangeEntity)
                .status(status)
                .build();
    }
}
