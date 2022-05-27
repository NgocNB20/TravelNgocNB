package com.ngocnb20.travel.repository;

import com.ngocnb20.travel.model.dto.resp.MenuRespDto;
import com.ngocnb20.travel.model.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BlogRepository extends BaseRepository<Blog,Long> , JpaSpecificationExecutor<Blog> {

    List<Blog> findAll();
    Optional<Blog> findById(Long id);
    @Query(value = "SELECT * FROM mytravel.blogs WHERE id NOT IN(:id) ORDER BY create_date Desc LIMIT 10 " ,nativeQuery = true)
    List<Blog> getBlogTop(@Param("id") Long id);
    @Modifying
    @Query("update Blog b set b.numberView = :numberView where b.id = :id")
    void updateView(@Param(value = "id") Long id, @Param(value = "numberView") int numberView);
    void deleteById(Long id);
    List<Blog> findByStatusBlog(boolean status);
    @Modifying
    @Query("update Blog b set b.numberLike = :numberLike where b.id = :id")
    void updateLike(@Param(value = "id") Long id, @Param(value = "numberLike") int numberLike);

}


