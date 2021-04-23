package com.cudo.mediabusiness.mediasite.repository;

import com.cudo.mediabusiness.mediasite.domain.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    /*@Query("SELECT port FROM Portfolio port where port.flatform = Portfolio")
    List<String> getflatform(String flat);*/

    /*Portfolio findPortfolioBy(Exposure show_check);*/

    @Query("SELECT port FROM Portfolio port where port.show_check = 'TRUE' order by port.show_sequence")
    List<Portfolio> findAllExposure();

    @Query("SELECT port FROM Portfolio port where port.show_check = 'FALSE' order by port.show_sequence")
    List<Portfolio> findAllNotExposure();

}
