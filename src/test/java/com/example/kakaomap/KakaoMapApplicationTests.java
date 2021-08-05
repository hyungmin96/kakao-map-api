package com.example.kakaomap;

import com.example.kakaomap.dto.WriterMapDTO;
import com.example.kakaomap.entity.GroupBoardDTO;
import com.example.kakaomap.entity.GroupBoardEntity;
import com.example.kakaomap.entity.GroupBoardFileEntity;
import com.example.kakaomap.entity.WriterExchangeEntity;
import com.example.kakaomap.repository.GroupBoardFileRepository;
import com.example.kakaomap.repository.GroupBoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application.yml")
class KakaoMapApplicationTests {

    @Autowired private GroupBoardRepository groupBoardRepository;
    @Autowired private GroupBoardFileRepository groupBoardFileRepository;

    @Test
    void 게시글_데이터_저장() {

        WriterMapDTO writerMapDTO = new WriterMapDTO();
        writerMapDTO.setLatitude("126.96034180602274");
        writerMapDTO.setLocation("서울 서대문구 북아현동 424");
        writerMapDTO.setLongitude("37.55969259036059");
        writerMapDTO.setResidence("서울 강남구 밤고개로 76-2 서울 강남구 밤고개로 76-2");
        writerMapDTO.setPreferTime("상관없음");
        WriterExchangeEntity writerExchangeEntity = writerMapDTO.toEntity();

        GroupBoardDTO groupBoardDTO = new GroupBoardDTO();
        groupBoardDTO.setContent("test 1");
        groupBoardDTO.setGroupId(1L);
        groupBoardDTO.setUsername("작성자1");
        groupBoardDTO.setCategory("exchange");
        groupBoardDTO.setWriterExchangeEntity(writerExchangeEntity);
        groupBoardDTO.setUserId(1L);

        GroupBoardEntity groupBoardEntity = groupBoardDTO.toEntity();

        groupBoardRepository.save(groupBoardEntity);

        for(int i = 0; i < 5; i ++){
            GroupBoardFileEntity groupBoardFileEntity =
                    GroupBoardFileEntity.builder()
                            .name("1")
                            .path("1")
                            .groupBoard(groupBoardEntity)
                            .build();

            groupBoardFileRepository.save(groupBoardFileEntity);
        }

    }

}
