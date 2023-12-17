package com.ransankul.priceaction.repositery;

import com.ransankul.priceaction.model.RelatedNews;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RelatedNewsRepo extends JpaRepository<RelatedNews,Long> {

    Optional<RelatedNews> findByNewsHeading(String newsHeading);

    List<RelatedNews> findAllByOrderByIdDesc(Pageable pageable);
}
