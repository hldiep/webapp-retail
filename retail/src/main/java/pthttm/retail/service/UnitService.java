package pthttm.retail.service;

import org.springframework.stereotype.Service;
import pthttm.retail.model.Unit;
import pthttm.retail.repository.UnitRepository;

import java.util.List;

@Service
public class UnitService {
    private final UnitRepository unitRepository;

    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public List<Unit> getAllUnit(){
        return (List<Unit>) unitRepository.findAll();
    }
}
