package com.practice.ordersystem.domain.Member.Controller;

import com.practice.ordersystem.domain.Member.DTO.MemberDetailResDto;
import com.practice.ordersystem.domain.Member.DTO.MemberListResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.practice.ordersystem.domain.Member.DTO.MemberCreateReqDto;
import com.practice.ordersystem.domain.Member.Service.MemberService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member/new")
    @CrossOrigin(originPatterns = "*")
    public HttpStatus createMember(@RequestBody MemberCreateReqDto memberCreateReqDto){
        try{
            memberService.save(memberCreateReqDto);
            return HttpStatus.OK;
        } catch(IllegalArgumentException e){
            return HttpStatus.BAD_REQUEST;
        }
    }

    @GetMapping("/members")
    @CrossOrigin(originPatterns = "*")
    public ResponseEntity<Object> memberList(){
        try{
            List<MemberListResDto> memberListResDtoList = memberService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(memberListResDtoList);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }

    @GetMapping("/member/{id}/orders")
    @CrossOrigin(originPatterns = "*")
    public ResponseEntity<Object> findMember(@PathVariable Long id){
        try{
            MemberDetailResDto memberDetailResDto = memberService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(memberDetailResDto);
        } catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }
}
