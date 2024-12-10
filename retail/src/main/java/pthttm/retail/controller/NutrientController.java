package pthttm.retail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pthttm.retail.model.Nutrient;
import pthttm.retail.model.ProductNutrient;
import pthttm.retail.service.NutrientService;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class NutrientController {
    @Autowired
    private NutrientService nutrientService;
    @GetMapping("/nutrient")
    public String getNutrientPage(Model model,
                                  @RequestParam(value = "sort", required = false) String sort,
                                  @RequestParam(value = "nutrientId", required = false) String nutrientId) {
        List<Nutrient> nutrients = nutrientService.findAllNutrients();
        if (nutrientId != null) {
            // Find the nutrient by ID
            Nutrient nutrientToSort = nutrients.stream()
                    .filter(nutrient -> nutrient.getId().equals(nutrientId))
                    .findFirst()
                    .orElse(null);

            if (nutrientToSort != null) {
                // Convert Collection to List to use sort method
                List<ProductNutrient> productNutrients = new ArrayList<>(nutrientToSort.getProducts());

                // Sort the products in this nutrient based on the sort option
                if ("price-up".equals(sort)) {
                    productNutrients.sort(Comparator.comparingLong(pn -> pn.getProduct().getPrice()));
                } else if ("price-down".equals(sort)) {
                    productNutrients.sort(Comparator.comparingLong((ProductNutrient pn) -> pn.getProduct().getPrice()).reversed());
                }

                // Set the sorted products back to the nutrient
                nutrientToSort.setProducts(new HashSet<>(productNutrients)); // Re-set as Collection (Set)
            }
        }

        model.addAttribute("nutrients", nutrients);
        model.addAttribute("sort", sort);
        return "danhmuc/nutrient";
    }
}
