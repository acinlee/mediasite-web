package com.cudo.mediabusiness.mediasite.repository;

import com.cudo.mediabusiness.mediasite.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class FileRepository {
    private final EntityManager entityManager;

    //파일 저장
    public Long save(File file){
        entityManager.persist(file);
        return file.getId();
    }

    //파일 조회
    public File findFile(Long id) {
        return entityManager.find(File.class, id);
    }

    //파일 삭제
    public void delete(File file){
        entityManager.remove(file);
    }
}
