package com.ngocnb20.travel.repository;

import com.ngocnb20.travel.model.entity.Hotel;
import com.ngocnb20.travel.model.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends BaseRepository<Restaurant,Long>, JpaSpecificationExecutor<Restaurant> {
    List<Restaurant> findAll();
    Optional<Restaurant> findById(Long id);
    @Query(value = "SELECT * FROM mytravel.restaurants WHERE id NOT IN(:id) ORDER BY number_view Desc LIMIT 5 ",nativeQuery = true)
    List<Restaurant> getRestaurantTop(@Param("id") Long id);
    @Modifying
    @Query("update Restaurant b set b.numberView = :numberView where b.id = :id")
    void updateView(@Param(value = "id") Long id, @Param(value = "numberView") int numberView);

    @Modifying
    @Query("update Restaurant b set b.numberLike = :numberLike where b.id = :id")
    void updateLike(@Param(value = "id") Long id, @Param(value = "numberLike") int numberLike);
}
