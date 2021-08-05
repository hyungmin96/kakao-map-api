package com.example.kakaomap.dto;

import com.example.kakaomap.entity.WriterExchangeEntity;
import lombok.*;

@Setter @Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WriterMapDTO {

    private String residence;
    private String longitude;
    private String latitude;
    private String location;
    private String preferTime;

    public WriterExchangeEntity toEntity(){
        return WriterExchangeEntity.builder()
                .residence(residence)
                .longitude(longitude)
                .location(location)
                .latitude(latitude)
                .exchangeTime(preferTime)
                .build();
    }

}
