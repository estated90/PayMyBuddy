package com.payMyBudy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.payMyBudy.dao.HolderDao;
import com.payMyBudy.model.Holder;
import com.payMyBudy.model.Role;

@Service
public class MemberService {

    @Autowired
    private HolderDao memberRepository;

    public Holder createMember(Holder member){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        member.setPassword(encoder.encode(member.getPassword()));
        Role memberRole = new Role("ADMIN");
        List<Role> roles = new ArrayList<>();
        roles.add(memberRole);
        member.setRole(roles);
        memberRepository.save(member);
        return member;
    }
	
}
