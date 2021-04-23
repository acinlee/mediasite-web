package com.cudo.mediabusiness.mediasite.repository;

import com.cudo.mediabusiness.mediasite.domain.MainPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MainPageRepository {
    private final EntityManager entityManager;

    
    //메인 페이지 신규 등록
    public Long save(MainPage mainPage){
        entityManager.persist(mainPage);
        return mainPage.getId();
    }

    public MainPage findById(Long id) {
        return entityManager.find(MainPage.class, id);
    }

    //전체 페이지 조회
    public List<MainPage> findAllMain() {
        return entityManager.createQuery("select mainpage from MainPage mainpage", MainPage.class)
                .getResultList();
    }

    //노출중인 페이지 조회
    public List<MainPage> findAllExposure() {
        return entityManager.createQuery("select mainpage from MainPage mainpage where mainpage.exposure_check = 'TRUE' order by mainpage.exposure_sequence", MainPage.class)
                .getResultList();
    }

    //미노출중인 페이지 조회
    public List<MainPage> findAllNotExposure() {
        return entityManager.createQuery("select mainpage from MainPage mainpage where mainpage.exposure_check = 'FALSE' order by mainpage.register_date", MainPage.class)
                .getResultList();
    }
}
