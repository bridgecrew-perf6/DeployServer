package edu.fsoft.spring.service;

import edu.fsoft.spring.interfaceService.ICategoryService;
import edu.fsoft.spring.model.Category;
import edu.fsoft.spring.model.Feedback;
import edu.fsoft.spring.model.News;
import edu.fsoft.spring.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository repo;
    public void save(Category category){
        category.setIsDeleted("false");
        repo.save(category);
    }

    public void deleteCategory(Category category){
        category.setIsDeleted("true");
        repo.save(category);
    }
    public List<Category> listAll() {
        return repo.listAllCategory();
    }
    public void delete(int id) {
        repo.deleteById(id);
    }
    public Category get(int id) {
        return repo.findById(id).get();
    }
}
