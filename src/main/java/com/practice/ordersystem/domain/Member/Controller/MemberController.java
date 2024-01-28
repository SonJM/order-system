package com.practice.ordersystem.domain.Member.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.practice.ordersystem.domain.Member.DTO.MemberCreateReqDto;
import com.practice.ordersystem.domain.Member.Service.MemberService;

@RestController
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/member/new")
    public ResponseEntity.BodyBuilder createMember(@RequestBody MemberCreateReqDto memberCreateReqDto){
        try{
            memberService.save(memberCreateReqDto);
            return ResponseEntity.status(HttpStatus.CREATED);
        } catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED);
        }
    }
}
