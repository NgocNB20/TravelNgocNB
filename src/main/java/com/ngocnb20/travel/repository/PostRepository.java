package com.ngocnb20.travel.repository;

import com.ngocnb20.travel.model.entity.Blog;
import com.ngocnb20.travel.model.entity.Post;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends BaseRepository<Post,Long> , JpaSpecificationExecutor<Post> {
    List<Post> findAll();
    Optional<Post> findById(Long id);
    @Query(value = "SELECT * FROM mytravel.posts WHERE id NOT IN(:id) ORDER BY create_date Desc LIMIT 10 " ,nativeQuery = true)
    List<Post> getPostTop(@Param("id") Long id);
    void deleteById(Long id);
    @Modifying
    @Query("update Post b set b.numberView = :numberView where b.id = :id")
    void updateView(@Param(value = "id") Long id, @Param(value = "numberView") int numberView);
    @Modifying
    @Query("update Post b set b.numberLike = :numberLike where b.id = :id")
    void updateLike(@Param(value = "id") Long id, @Param(value = "numberLike") int numberLike);

}
