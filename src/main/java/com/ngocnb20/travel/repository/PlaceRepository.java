package com.ngocnb20.travel.repository;

import com.ngocnb20.travel.model.entity.Blog;
import com.ngocnb20.travel.model.entity.Place;
import com.ngocnb20.travel.model.entity.Tour;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PlaceRepository extends BaseRepository<Place,Long>, JpaSpecificationExecutor<Place> {
    List<Place> findAll();
    Optional<Place> findById(Long id);
    List<Place> findTop6ByOrderByNumberViewDesc();
    @Query(value = "SELECT * FROM mytravel.places WHERE id NOT IN(:id) ORDER BY create_date Desc LIMIT 10 " ,nativeQuery = true)
    List<Place> getPlaceTop(@Param("id") Long id);
    void deleteById(Long id);
    @Modifying
    @Query("update Place b set b.numberView = :numberView where b.id = :id")
    void updateView(@Param(value = "id") Long id, @Param(value = "numberView") int numberView);
    @Modifying
    @Query("update Place b set b.numberLike = :numberLike where b.id = :id")
    void updateLike(@Param(value = "id") Long id, @Param(value = "numberLike") int numberLike);
}
