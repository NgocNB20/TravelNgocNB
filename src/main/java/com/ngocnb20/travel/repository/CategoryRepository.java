package com.ngocnb20.travel.repository;

import com.ngocnb20.travel.model.entity.Blog;
import com.ngocnb20.travel.model.entity.Category;
import com.ngocnb20.travel.model.entity.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;


public interface CategoryRepository extends BaseRepository<Category,Long>{

    List<Category> findAll();
    @Query("SELECT c FROM Category c inner join CategoryPlace  cp on c.id =cp.category.id where cp.place.id=:idPlace")
    public Set<Category> getCategoryByPlace(@Param("idPlace") Long idPlace);

}

