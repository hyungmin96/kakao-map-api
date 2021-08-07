package com.example.kakaomap.api;

import com.example.kakaomap.dto.ClientExchangeDTO;
import com.example.kakaomap.dto.GroupBoardDTO;
import com.example.kakaomap.entity.ClientExchangeEntity;
import com.example.kakaomap.entity.GroupBoardEntity;
import com.example.kakaomap.entity.GroupBoardFileEntity;
import com.example.kakaomap.service.GroupExchangeService;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange")
public class GroupExchangeApiController {

    private final GroupExchangeService groupExchangeService;

    @PostMapping("/select/request")
    public void selectRequestEntity(ClientExchangeDTO clientExchangeDTO){
        groupExchangeService.selectRequest(clientExchangeDTO);
    }

    @GetMapping("/get_request_list")
    public Page<ResponseClientDTO> getClientRequestList(GroupBoardDTO groupBoardDTO) {
        Pageable page = PageRequest.of(groupBoardDTO.getPage(), groupBoardDTO.getDisplay(), Sort.Direction.ASC, "id");
        JPAQuery<ResponseClientDTO> query = groupExchangeService.getClientRequestList(groupBoardDTO, page);
        return new PageImpl<>(query.fetch(), page, query.fetchCount());
    }

    @PostMapping("/client/post")
    public ResponseClientDTO clientPost(ClientExchangeDTO clientExchangeDTO){
        ClientExchangeEntity clientExchangeEntity = groupExchangeService.clientPost(clientExchangeDTO);
        return new ResponseClientDTO(clientExchangeEntity);
    }

    @PostMapping("/post") // writer 게시글 작성 api
    public ResponseBoardDTO postGroupBoard(GroupBoardDTO groupBoardDTO){
        GroupBoardEntity boardItem = groupExchangeService.wrtierPost(groupBoardDTO);
        return new ResponseBoardDTO(boardItem);
    }

    @GetMapping("/view/board")
    public ResponseBoardDTO getExchangeInfo(GroupBoardDTO groupBoardDTO){
        GroupBoardEntity groupBoardEntity = groupExchangeService.getExchangeInfo(groupBoardDTO);
        return new ResponseBoardDTO(groupBoardEntity);
    }

    @Setter @Getter
    @NoArgsConstructor
    public static class ResponseClientDTO{
        private Long clientId;
        private Long userId;
        private Long processId; // 거래가 진행중인 entity index
        private Long writerId;
        private String username;
        private String userProfile;
        private String content;
        private String price;
        private String request;
        private List<String> file;

        public ResponseClientDTO(ClientExchangeEntity entity) {
            this.userId = entity.getUserId();
            this.content = entity.getContent();
            this.price = entity.getPrice();
            this.request = entity.getRequest();
        }
    }


    @Setter @Getter
    static class ResponseBoardDTO {
        // 게시글 DTO
        private String result;
        private Long userId;
        private String username;
        private String profilePath;
        private LocalDateTime regTime;
        private Long boardId;
        private int boardLike;
        private String content;
        private GroupBoardEntity.BoardType type; // 그룹 공지, 일반 글
        private List<String> boardFiles;

        // 게시글 거래위치 DTO
        private String residence;
        private String longitude;
        private String latitude;
        private String location;
        private String preferTime;

        public ResponseBoardDTO(GroupBoardEntity entity) {
            this.regTime = entity.getRegTime();
            this.boardId = entity.getBoardId();
            this.boardLike = entity.getBoardLike();
            this.type = entity.getType();
            this.content = entity.getContent();
            this.boardFiles = entity.getFiles().stream().map(GroupBoardFileEntity::getName).collect(Collectors.toList());
            if(entity.getExchange() != null){
                this.residence = entity.getExchange().getResidence();
                this.longitude = entity.getExchange().getLongitude();
                this.latitude = entity.getExchange().getLatitude();
                this.location = entity.getExchange().getLocation();
                this.preferTime = entity.getExchange().getExchangeTime();
            }
        }
    }

}
