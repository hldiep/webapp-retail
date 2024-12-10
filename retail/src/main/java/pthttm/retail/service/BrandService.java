package pthttm.retail.service;

import org.springframework.stereotype.Service;
import pthttm.retail.model.Brand;
import pthttm.retail.repository.BrandRepository;

import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> getAllBrand(){
        return (List<Brand>) brandRepository.findAll();
    }
}
