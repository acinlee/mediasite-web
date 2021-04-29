package com.cudo.mediabusiness.mediasite.repository;

import com.cudo.mediabusiness.mediasite.domain.Portfolio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class PortfolioRepository{
    private final EntityManager entityManager;

    //저장
    public Long save(Portfolio portfolio){
        entityManager.persist(portfolio);
        return portfolio.getId();
    }

    //전체 요청 불러오기
    public List<Portfolio> findAll(){
        return entityManager.createQuery("select portfolio from Portfolio portfolio", Portfolio.class)
                .getResultList();
    }

    public Portfolio findById(Long id) {
        return entityManager.find(Portfolio.class, id);
    }

    //노출함
    public List<Portfolio> findAllExposure(){
        return entityManager.createQuery("select portfolio from Portfolio portfolio where portfolio.show_check = 'TRUE' order by portfolio.show_sequence", Portfolio.class)
                .getResultList();
    }

    //노출안함
    public List<Portfolio> findAllNotExposure(){
        return entityManager.createQuery("select portfolio from Portfolio portfolio where portfolio.show_check = 'FALSE' order by portfolio.show_sequence", Portfolio.class)
                .getResultList();
    }

    //페이징 처리
   /* public Page<Portfolio> findAll(Pageable pageable) {
        return pageable.toString();
    }*/
}
