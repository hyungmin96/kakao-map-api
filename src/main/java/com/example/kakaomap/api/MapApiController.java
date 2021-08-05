package com.example.kakaomap.api;

import com.example.kakaomap.dto.WriterMapDTO;
import com.example.kakaomap.entity.WriterExchangeEntity;
import com.example.kakaomap.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange")
public class MapApiController {

    private final MapService mapService;

    @PostMapping("/save/writer/location")
    public WriterExchangeEntity saveWriterLocation(WriterMapDTO mapDTO){
        return mapService.saveWriterLocation(mapDTO);
    }

}
