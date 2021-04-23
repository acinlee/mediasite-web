package com.cudo.mediabusiness.mediasite.repository;

import com.cudo.mediabusiness.mediasite.domain.Introduction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class IntroductionRepository {
    private final EntityManager entityManager;

    public Long save(Introduction introduction) {
        entityManager.persist(introduction);
        return introduction.getId();
    }

    public Introduction findById(Long id) {
        return entityManager.find(Introduction.class, id);
    }

    public List<Introduction> findAllList() {
        return entityManager.createQuery("select intro from Introduction intro order by intro.register_date DESC", Introduction.class)
                .getResultList();
    }

    public Introduction findRecentlyist() {
        List<Introduction> introductions =  entityManager.createQuery("select intro from Introduction intro order by intro.register_date DESC", Introduction.class).getResultList();
        Introduction introduction = introductions.get(0);
        return introduction;
    }
}
