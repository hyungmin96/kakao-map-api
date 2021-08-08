package com.example.kakaomap.controller;

import com.example.kakaomap.dto.GroupBoardDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GroupExchangeController {

    @GetMapping("/writer/map")
    public String getHome(){
        return "/common/wrtiermap";
    }

    // 게시글 작성 controller
    @GetMapping("/post/board")
    public String getRegistInfo(){
        return "/common/exchangeinfo";
    }

    @GetMapping("/view/exchange/list")
    public String getExchanges(){
        return "/common/exchangeview";
    }

    @GetMapping("/request/exchange/{boardId}")
    public String getBoardInfo(Model model, GroupBoardDTO groupBoardDTO){
        model.addAttribute("board", groupBoardDTO.getBoardId());
        return "/common/clientmap";
    }

    @GetMapping("/get_request_list/{boardId}")
    public String getRequest(Model model, @PathVariable Long boardId){
        model.addAttribute("boardId", boardId);
        return "common/requestlist";
    }

    @GetMapping("/")
    public String getBoardList(){
        return "/common/boardList";
    }

}
