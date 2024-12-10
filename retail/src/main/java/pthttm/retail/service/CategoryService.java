package pthttm.retail.service;


import org.springframework.stereotype.Service;
import pthttm.retail.model.Category;
import pthttm.retail.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategory(){
        return (List<Category>) categoryRepository.findAll();
    }
}
