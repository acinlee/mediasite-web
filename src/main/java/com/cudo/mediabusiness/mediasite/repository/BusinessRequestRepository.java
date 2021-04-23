package com.cudo.mediabusiness.mediasite.repository;

import com.cudo.mediabusiness.mediasite.domain.BusinessRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BusinessRequestRepository {
    private final EntityManager entityManager;

    //저장
    public Long saveRequest(BusinessRequest businessRequest) {
        entityManager.persist(businessRequest);
        return businessRequest.getId();
    }

    //전체 요청 불러오기
    public List<BusinessRequest> findAllRequest(){
        return entityManager.createQuery("select request from BusinessRequest request", BusinessRequest.class)
                .getResultList();
    }

    public BusinessRequest findById(Long id) {
        return entityManager.find(BusinessRequest.class, id);
    }
}
