package com.practice.ordersystem.domain.Member.Service;

import com.practice.ordersystem.domain.Member.Address;
import com.practice.ordersystem.domain.Member.DTO.MemberListResDto;
import com.practice.ordersystem.domain.Member.DTO.MemberOrderListResDto;
import com.practice.ordersystem.domain.Member.Member;
import com.practice.ordersystem.domain.Ordering.Ordering;
import com.practice.ordersystem.domain.Ordering.Repository.OrderRepository;
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
    private final OrderRepository orderRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, OrderRepository orderRepository) {
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
    }


    public Member save(MemberCreateReqDto memberCreateReqDto){
        Address address = new Address(
                memberCreateReqDto.getCity(),
                memberCreateReqDto.getStreet(),
                memberCreateReqDto.getZipcode()
        );
        Member member = Member.builder()
                .name(memberCreateReqDto.getName())
                .email(memberCreateReqDto.getEmail())
                .password(memberCreateReqDto.getPassword())
                .address(address)
                .role(Role.USER)
                .build();
        return memberRepository.save(member);
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

    public List<MemberOrderListResDto> findById(Long id) throws EntityNotFoundException{
        Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        List<Ordering> orderingList = orderRepository.findAllByMemberId(id);
        List<MemberOrderListResDto> memberDetailResDtos = new ArrayList<>();
        for(Ordering ordering : orderingList){
            MemberOrderListResDto memberDetailResDto = MemberOrderListResDto.builder()
                    .name(member.getName())
                    .orderId(ordering.getId())
                    .orderStatus(ordering.getStatus().name())
                    .build();
            memberDetailResDtos.add(memberDetailResDto);
        }
        return memberDetailResDtos;
    }
}
