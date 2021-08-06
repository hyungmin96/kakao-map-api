package com.example.kakaomap.api;

import com.example.kakaomap.dto.ClientExchangeDTO;
import com.example.kakaomap.dto.GroupBoardDTO;
import com.example.kakaomap.entity.ClientExchangeEntity;
import com.example.kakaomap.entity.ExchangeFileEntity;
import com.example.kakaomap.entity.GroupBoardEntity;
import com.example.kakaomap.entity.GroupBoardFileEntity;
import com.example.kakaomap.service.GroupExchangeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange")
public class GroupExchangeApiController {

    private final GroupExchangeService groupExchangeService;

    @GetMapping("/get_request_list")
    public Page<ResponseClientDTO> getClientRequestList(GroupBoardDTO groupBoardDTO){
        PageRequest page = PageRequest.of(groupBoardDTO.getPage(), groupBoardDTO.getDisplay(), Sort.Direction.ASC, "id");
        List<ResponseClientDTO> result = groupExchangeService.getClientRequestList(groupBoardDTO)
                 .stream().map(ResponseClientDTO::new).collect(Collectors.toList());
         return new PageImpl<>(result, page, result.size());
    }

    @PostMapping("/client/post")
    public ResponseExchangeDTO clientPost(ClientExchangeDTO clientExchangeDTO){
        ClientExchangeEntity clientExchangeEntity = groupExchangeService.clientPost(clientExchangeDTO);
        return new ResponseExchangeDTO(clientExchangeEntity);
    }

    @PostMapping("/post") // writer 게시글 작성 api
    public ResponseBoardDTO postGroupBoard(GroupBoardDTO groupBoardDTO){
        GroupBoardEntity boardItem = groupExchangeService.wrtierPost(groupBoardDTO);
        return new ResponseBoardDTO(boardItem);
    }

    @GetMapping("/get_exchang_list")
    public List<ResponseBoardDTO> getExchangeBoards(GroupBoardDTO groupBoardDTO){
        List<GroupBoardEntity> groupBoardEntities = groupExchangeService.getExchangeBoards(groupBoardDTO);
        return groupBoardEntities.stream().map(ResponseBoardDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/view/board")
    public ResponseBoardDTO getExchangeInfo(GroupBoardDTO groupBoardDTO){
        GroupBoardEntity groupBoardEntity = groupExchangeService.getExchangeInfo(groupBoardDTO);
        return new ResponseBoardDTO(groupBoardEntity);
    }

    @Setter @Getter
    static class ResponseClientDTO{

        private Long userId;
        private String content;
        private String price;
        private String request;
        private List<String> file;

        public ResponseClientDTO(ClientExchangeEntity entity){
            this.userId = entity.getUserId();
            this.content = entity.getContent();
            this.price = entity.getPrice();
            this.request = entity.getRequest();
            this.file = entity.getFiles().stream().map(ExchangeFileEntity::getName).collect(Collectors.toList());
        }
    }

    @Setter @Getter
    static class ResponseExchangeDTO {
        private Long userId;
        private String content;
        private String price;
        private String request;

        public ResponseExchangeDTO(ClientExchangeEntity entity){
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
