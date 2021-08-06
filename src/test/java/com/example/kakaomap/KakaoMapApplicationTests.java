package com.example.kakaomap;

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



    }

}
