package com.practice.ordersystem.domain.Member.Service;

import com.practice.ordersystem.domain.Member.DTO.MemberDetailResDto;
import com.practice.ordersystem.domain.Member.DTO.MemberListResDto;
import com.practice.ordersystem.domain.Member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.practice.ordersystem.domain.Member.Repository.MemberRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import com.practice.ordersystem.domain.Member.DTO.MemberCreateReqDto;
import com.practice.ordersystem.domain.Member.Role;

import java.util.ArrayList;
import java.util.List;

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
        if(memberCreateReqDto.getRole().equals("admin")){
            role = Role.ADMIN;
        } else role = Role.USER;
        Member member = Member.builder()
                .name(memberCreateReqDto.getName())
                .email(memberCreateReqDto.getEmail())
                .address(memberCreateReqDto.getAddress())
                .password(memberCreateReqDto.getPassword())
                .role(role)
                .build();
       memberRepository.save(member);
    }

    public List<MemberListResDto> findAll() throws NullPointerException{
        List<Member> members = memberRepository.findAll();
        List<MemberListResDto> memberListResDtoList = new ArrayList<>();
        for(Member member : members){
            String role = "";
            if(member.getRole().equals(Role.ADMIN)) role = "관리자";
            else role = "유저";
            MemberListResDto memberListResDto = MemberListResDto.builder()
                    .name(member.getName())
                    .createdTime(member.getCreatedTime())
                    .role(role)
                    .build();
            try{
                memberListResDtoList.add(memberListResDto);
            } catch (NullPointerException e){
                throw new NullPointerException("멤버가 없습니다.");
            }
        }
        return memberListResDtoList;
    }

    public MemberDetailResDto findById(Long id) throws EntityNotFoundException{
        Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        String role = "";
        if(member.getRole().equals(Role.ADMIN)) role = "관리자";
        else role = "유저";
        return MemberDetailResDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .address(member.getAddress())
                .createdTime(member.getCreatedTime())
                .role(role)
                // Ordering 엔티티의 member_id와 매핑
                .orderingList(member.getOrders())
                .build();
    }
}
