package com.ngocnb20.travel.repository;

import com.ngocnb20.travel.model.entity.Blog;
import com.ngocnb20.travel.model.entity.Place;
import com.ngocnb20.travel.model.entity.Tour;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TourRepository extends BaseRepository<Tour,Long>, JpaSpecificationExecutor<Tour> {
    List<Tour> findAll();
    Optional<Tour> findById(Long id);
    @Modifying
    @Query("update Tour b set b.numberView = :numberView where b.id = :id")
    void updateView(@Param(value = "id") Long id, @Param(value = "numberView") int numberView);
    @Modifying
    @Query("update Tour b set b.numberLike = :numberLike where b.id = :id")
    void updateLike(@Param(value = "id") Long id, @Param(value = "numberLike") int numberLike);


}
