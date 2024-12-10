package pthttm.retail.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pthttm.retail.model.Brand;
import pthttm.retail.model.Product;
import pthttm.retail.service.ProductService;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class TestController {

    private final ProductService productService;
    public TestController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/header")
    public String getHeader(Model model) throws IOException {
        return "comp-header";
    }
    @GetMapping("/")
    public String getIndex(Model model) throws IOException {
        return "index";
    }

    @GetMapping("/cart")
    public String getCartPage(){
        return "page-cart";
    }
//    @GetMapping("/search")
//    public String getSearchPage(@RequestParam(required = false) String sort,
//                                @RequestParam(required = false) Double minPrice,
//                                @RequestParam(required = false) Double maxPrice,
//                                @RequestParam(required = false) List<String> brandIds,
//                                Model model){
//        List<Product> products = productService.getAllProduct();
//        Logger logger = LoggerFactory.getLogger(this.getClass());
//        if (products == null || products.isEmpty()) {
//            logger.info("No products found.");
//        } else {
//            model.addAttribute("products",products);
//            products.forEach(product -> logger.info(product.toString()));
//        }
//
//        if ("price-up".equals(sort)) {
//            products.sort(Comparator.comparingDouble(Product::getPrice));
//        } else if ("price-down".equals(sort)) {
//            products.sort(Comparator.comparingDouble(Product::getPrice).reversed());
//        }
//        if (minPrice != null && maxPrice != null) {
//            products = products.stream()
//                    .filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
//                    .collect(Collectors.toList());
//        }
//
//        Set<Brand> brands = products.stream()
//                .map(Product::getBrand)
//                .collect(Collectors.toSet());
//
//        model.addAttribute("products", products);
//        model.addAttribute("brands", brands);
//        return "search-page";
//    }
}
