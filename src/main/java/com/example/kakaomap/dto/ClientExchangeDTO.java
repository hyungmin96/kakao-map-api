package com.example.kakaomap.dto;

import com.example.kakaomap.entity.ClientExchangeEntity;
import com.example.kakaomap.entity.ExchangeFileEntity;
import com.example.kakaomap.entity.WriterExchangeEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter
public class ClientExchangeDTO implements Serializable {

    private Long clientId;
    private Long userId;
    private String username;
    private String userProfile;
    private Long boardId; // 교환을 요청한 게시글 번호
    private String content;
    private String price;
    private String request;
    private MultipartFile[] images;
    private List<ExchangeFileEntity> files = new ArrayList<>();

    public void addFile(ExchangeFileEntity file){
        this.files.add(file);
    }

    public ClientExchangeEntity toEntity(WriterExchangeEntity writerExchangeEntity){
            return ClientExchangeEntity
                    .builder()
                    .content(content)
                    .writerExchangeEntity(writerExchangeEntity)
                    .request(request)
                    .price(price)
                    .userId(userId)
                    .build();
    }
}
