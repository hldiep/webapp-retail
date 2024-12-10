package pthttm.retail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pthttm.retail.model.Brand;
import pthttm.retail.model.Product;
import pthttm.retail.service.ProductService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    private final ProductService productService;
    @Autowired  // Đảm bảo @Autowired có mặt ở constructor
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/bestselling")
    public String showPopularProducts(@RequestParam(value = "sort", required = false) String sort, Model model) {
        List<Product> popularProducts = productService.getPopularProducts();

        if ("price-up".equals(sort)) {
            popularProducts.sort(Comparator.comparingLong(Product::getPrice));
        } else if ("price-down".equals(sort)) {
            popularProducts.sort(Comparator.comparingLong(Product::getPrice).reversed());
        }

        model.addAttribute("popularProducts", popularProducts);
        model.addAttribute("sort", sort);
        return "danhmuc/bestselling";
    }

    @GetMapping("/newfruit")
    public String getAllProductsSortedByDate(@RequestParam(value = "sort", required = false) String sort, Model model) {
        List<Product> sortedProducts = productService.getAllProductsSortedByDate();
        if ("price-up".equals(sort)) {
            sortedProducts.sort(Comparator.comparingLong(Product::getPrice));
        } else if ("price-down".equals(sort)) {
            sortedProducts.sort(Comparator.comparingLong(Product::getPrice).reversed());
        }
        model.addAttribute("products", sortedProducts); // Thêm danh sách sản phẩm đã sắp xếp vào model
        model.addAttribute("sort", sort);
        return "danhmuc/newfruit"; // Tên view để hiển thị sản phẩm
    }

    @GetMapping("/inland")
    public String getProductsByCategoryTN(@RequestParam(value = "sort", required = false) String sort, Model model) {
        List<Product> products = productService.getProductsByCategoryTN();
        if ("price-up".equals(sort)) {
            products.sort(Comparator.comparingLong(Product::getPrice));
        } else if ("price-down".equals(sort)) {
            products.sort(Comparator.comparingLong(Product::getPrice).reversed());
        }
        model.addAttribute("products", products); // Thêm danh sách sản phẩm vào model
        model.addAttribute("sort", sort);
        return "danhmuc/inland"; // Tên view để hiển thị sản phẩm
    }

//    @GetMapping("/search_ml")
//    public String search(@RequestParam("txtSearch") String query, Model model) {
//        // Lấy danh sách các ID sản phẩm từ API Django
//        List<String> productIds = productService.searchProductIds(query);
//        List<Product> products= new ArrayList<>();
//        if(!productIds.isEmpty()){
//            products = productService.findAllById(productIds);
//        }else{
//            products = productService.findByName(query);
//        }
//        // Giả sử bạn đã có phương thức để tìm sản phẩm từ DB theo các ID này
//
//        Set<Brand> brands=null;
//        if(products!=null){
//            brands=products.stream()
//                    .map(Product::getBrand)
//                    .collect(Collectors.toSet());
//        }
//        // Thêm danh sách sản phẩm vào Model để truyền cho view
//        model.addAttribute("products", products);
//        model.addAttribute("brands", brands);
//        return "search-page";
//    }

    @GetMapping("/search_ml")
    public String search(@RequestParam(name = "txtSearch", required = false) String query,
                         @RequestParam(name = "sortOrder", required = false) String sortOrder,
                         @RequestParam(name = "minPrice", required = false) Integer minPrice,
                         @RequestParam(name = "maxPrice", required = false) Integer maxPrice,
                         @RequestParam(name = "brandIds", required = false) List<String> brandIds, // Nhận tham số mảng brandIds
                         Model model) {
        List<Product> products = new ArrayList<>();

        if (query != null && !query.isEmpty()) {

            products = productService.findByName(query);
            if(products.isEmpty()){
                System.out.println("Gọi API tìm kiếm.");
                List<String> productIds = productService.searchProductIds(query);
                if (!productIds.isEmpty()) {
                    products = productService.findAllById(productIds);
                }
            }
        } else {
            products = productService.getAllProduct();
        }

        // Lọc theo giá
        if (minPrice != null || maxPrice != null) {
            final int min = (minPrice != null) ? minPrice : Integer.MIN_VALUE;
            final int max = (maxPrice != null) ? maxPrice : Integer.MAX_VALUE;
            products = products.stream()
                    .filter(product -> product.getPrice() >= min && product.getPrice() <= max)
                    .collect(Collectors.toList());
        }

        // Lọc theo thương hiệu nếu có
        if (brandIds != null && !brandIds.isEmpty()) {
            products = products.stream()
                    .filter(product -> brandIds.contains(product.getBrand().getId()))
                    .collect(Collectors.toList());
        }

        // Áp dụng sắp xếp
        if ("asc".equals(sortOrder)) {
            products.sort(Comparator.comparing(Product::getPrice));
        } else if ("desc".equals(sortOrder)) {
            products.sort(Comparator.comparing(Product::getPrice).reversed());
        } else if ("name".equals(sortOrder)) {
            products.sort(Comparator.comparing(Product::getName));
        }

        // Lấy danh sách các thương hiệu đã lọc
        Set<Brand> brands = null;
        if (!products.isEmpty()) {
            brands = products.stream()
                    .map(Product::getBrand)
                    .collect(Collectors.toSet());
        }

        model.addAttribute("products", products);
        model.addAttribute("brands", brands);
        model.addAttribute("query", query);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("selectedBrandIds", brandIds);  // Truyền lại brandIds đã chọn

        return "search-page";
    }
}
