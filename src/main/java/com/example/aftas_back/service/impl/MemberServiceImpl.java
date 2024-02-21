package com.example.aftas_back.service.impl;

import com.example.aftas_back.domain.User;
import com.example.aftas_back.repository.MemberRepository;
import com.example.aftas_back.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;


    @Override
    public User save(User member) {
        return memberRepository.save(member);
    }

    @Override
    public List<User> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public User update(User memberUpdated, Long id) {
        Optional<User> user = memberRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setName(memberUpdated.getName());
            existingUser.setFamilyName(memberUpdated.getFamilyName());
            existingUser.setAccessionDate(memberUpdated.getAccessionDate());
            existingUser.setNationality(memberUpdated.getNationality());
            existingUser.setNationality(memberUpdated.getNationality());
            existingUser.setIdentityDocumentType(memberUpdated.getIdentityDocumentType());
            existingUser.setIdentityDocumentType(memberUpdated.getIdentityDocumentType());
            existingUser.setIdentityNumber(memberUpdated.getIdentityNumber());
            return memberRepository.save(existingUser);
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @Override
    public User getById(Long id) {
        return memberRepository.getMemberById(id);
    }

    @Override
    public List<User> searchMembers(Long id, String name, String familyName) {
        return memberRepository.searchMembers(id, name, familyName);
    }
}