package com.example.kakaomap.service;

import com.example.kakaomap.dto.WriterMapDTO;
import com.example.kakaomap.entity.WriterExchangeEntity;
import com.example.kakaomap.repository.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapService {

    private final MapRepository mapRepository;

    public WriterExchangeEntity saveWriterLocation(WriterMapDTO mapDTO){
        WriterExchangeEntity writerExchangeEntity = mapDTO.toEntity();
        mapRepository.save(writerExchangeEntity);
        return writerExchangeEntity;
    }

}
