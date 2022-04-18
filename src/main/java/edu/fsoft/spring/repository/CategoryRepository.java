package edu.fsoft.spring.repository;

import edu.fsoft.spring.model.Category;
import edu.fsoft.spring.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value="SELECT * FROM category where category.is_deleted = 'false';", nativeQuery = true)
    List<Category> listAllCategory();
}
