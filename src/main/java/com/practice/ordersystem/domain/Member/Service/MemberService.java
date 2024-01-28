package com.practice.ordersystem.domain.Member.Service;

import com.practice.ordersystem.domain.Member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.practice.ordersystem.domain.Member.Repository.MemberRepository;

import javax.transaction.Transactional;
import com.practice.ordersystem.domain.Member.DTO.MemberCreateReqDto;
import com.practice.ordersystem.domain.Member.Role;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public void save(MemberCreateReqDto memberCreateReqDto){
        if(memberRepository.findByEmail(memberCreateReqDto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이메일이 중복입니다");
        }
        Role role = null;
        if(memberCreateReqDto.getRole().equals("관리자")){
            role = Role.ADMIN;
        } else role = Role.USER;
        Member member = Member.builder()
                .name(memberCreateReqDto.getName())
                .email(memberCreateReqDto.getEmail())
                .address(memberCreateReqDto.getAddress())
                .password(memberCreateReqDto.getPassword())
                .role(role)
                .build();
        try{
            memberRepository.save(member);
        } catch(Exception e){
            throw e;
        }
    }
}
