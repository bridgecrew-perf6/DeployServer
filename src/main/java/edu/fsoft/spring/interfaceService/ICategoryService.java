package edu.fsoft.spring.interfaceService;

import edu.fsoft.spring.model.Category;

import java.util.List;

public interface ICategoryService {
    public void save(Category feedback);
    public List<Category> listAll();
    public void delete(int id);
    public Category get(int id);
}
