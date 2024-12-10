package pthttm.retail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pthttm.retail.model.Nutrient;
import pthttm.retail.model.ProductNutrient;
import pthttm.retail.repository.NutrientRepository;
import pthttm.retail.repository.ProductNutrientRepository;

import java.util.List;
@Service
public class NutrientService {
    @Autowired
    private NutrientRepository nutrientRepository;

    @Autowired
    private ProductNutrientRepository productNutrientRepository;

    public List<Nutrient> findAllNutrients() {
        return nutrientRepository.findAll();
    }

}
