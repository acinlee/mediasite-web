package com.cudo.mediabusiness.mediasite.repository;

import com.cudo.mediabusiness.mediasite.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager entityManager;

    //user 저장
    public void save(Member member) {
        entityManager.persist(member);
    }

    //특정 유저 검색, by id
    public Member findMemberById(String id) {
        return entityManager.find(Member.class, id);
    }

    //특정 유저 검색, by name
    public List<Member> findMemberByName(String name) {
        return entityManager.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    //전체 유저 검색
    public List<Member> findAllMember() {
        return entityManager.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    //회원 정보 수정
    public void update(){}

    //회원 정보 삭제
    public void delete(){}
}
