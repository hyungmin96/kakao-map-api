package com.example.kakaomap.api;

import com.example.kakaomap.dto.ClientExchangeDTO;
import com.example.kakaomap.dto.GroupBoardDTO;
import com.example.kakaomap.entity.*;
import com.example.kakaomap.service.GroupExchangeService;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.*;
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

    @PostMapping("/cancel/request")
    public void cancelRequestEntity(ClientExchangeDTO clientExchangeDTO){
        groupExchangeService.cancelRequest(clientExchangeDTO);
    }

    @PostMapping("/select/request")
    public ResponseRequestExchangeDTO selectRequestEntity(ClientExchangeDTO clientExchangeDTO){
        return groupExchangeService.selectRequest(clientExchangeDTO);
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

    @GetMapping("/get_board_list")
    public Page<ResponseBoardDTO> getBoardList(GroupBoardDTO groupBoardDTO){
        Pageable page = PageRequest.of(groupBoardDTO.getPage(), groupBoardDTO.getDisplay(), Sort.Direction.ASC, "id");
        JPAQuery<ResponseBoardDTO> query = groupExchangeService.getBoardList(groupBoardDTO, page);
        return new PageImpl<>(query.fetch(), page, query.fetchCount());
    }

    @Setter @Getter
    public static class ResponseRequestExchangeDTO{

        private Long userId; // writer id
        private String username;
        private String userProfile;
        private Long clientUserId;
        private String clilentUsername;
        private String clientProfile;
        private Long boardId;
        private Long clientId;

        public ResponseRequestExchangeDTO(WriterClientJoinEntity entity){
            this.userId = entity.getWriterId();
//            this.username = entity.getContent();
//            this.userProfile = entity.getPrice();
            this.clientUserId = entity.getClientId();
//            this.clilentUsername = entity.getUserId();
//            this.clientProfile = entity.getContent();
            this.boardId = entity.getWriterExchangeEntity().getId();
            this.clientId = entity.getClientExchangeEntity().getId();
        }
    }

    @Setter @Getter
    @NoArgsConstructor
    public static class ResponseClientDTO{
        private Long clientId;
        private Long userId;
        private Long writerId;
        private String username;
        private String userProfile;
        private String content; // client's product description
        private String price; // add price with product or none product
        private String request; // client's request content
        private WriterClientJoinEntity.status status; // To check exchange was progressing
        private List<String> file;

        public ResponseClientDTO(ClientExchangeEntity entity) {
            this.userId = entity.getUserId();
            this.content = entity.getContent();
            this.price = entity.getPrice();
            this.request = entity.getRequest();
        }
    }


    @Setter @Getter
    public static class ResponseBoardDTO {
        // 게시글 DTO
        private String result;
        private Long userId;
        private String username;
        private String profilePath;
        private LocalDateTime regTime;
        private Long boardId;
        private int boardLike;
        private GroupBoardEntity.BoardCategory BoardCategory;
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
            this.BoardCategory = entity.getBoardCategory();
            this.type = entity.getType();
            this.content = entity.getContent();
            this.boardFiles = entity.getFiles().stream().map(GroupBoardFileEntity::getName).collect(Collectors.toList());
            if (entity.getWriterExchangeEntity() != null) {
                this.residence = entity.getWriterExchangeEntity().getResidence();
                this.longitude = entity.getWriterExchangeEntity().getLongitude();
                this.latitude = entity.getWriterExchangeEntity().getLatitude();
                this.location = entity.getWriterExchangeEntity().getLocation();
                this.preferTime = entity.getWriterExchangeEntity().getExchangeTime();
            }
        }
    }

}
