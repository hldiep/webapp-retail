package pthttm.retail.service;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pthttm.retail.model.Product;
import pthttm.retail.repository.OrderItemRepository;
import pthttm.retail.repository.ProductRepository;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> getAllProduct(){
        return (List<Product>) productRepository.findAll();
    }
    public List<Product> findAllById(List<String> productId){
        return (List<Product>) productRepository.findAllById(productId);
    }

    //https://www.codejava.net/frameworks/spring-boot/spring-data-jpa-filter-search-examples
    public Product getProductById(String productId){
        return (Product) productRepository.findById(productId).orElse(null);
    }

    public List<Product> fillProductByOutOfStock(){
        return (List<Product>) productRepository.findByQuantity(0);
    }

    public Product saveProduct(Product product) throws Exception{
//        product.setLastUpdate(LocalDateTime.now());
        return productRepository.save(product);
    }

    public void deleteProduct(String productId){
        Product product = productRepository.findById(productId).orElse(null);
        product.setFlag(true);
        productRepository.save(product);
    }

    public String createAutoId(){
        List<Product> products = (List<Product>) productRepository.findAll();
        int newProductNumber;
        if(products.size()<1) newProductNumber= 1;
        else {
            String currentProductId = products.get(products.size() - 1).getId().strip();
            newProductNumber = Integer.parseInt(currentProductId.substring(2)) + 1;
        }
        return String.format("SP%06d",newProductNumber);//%06d: nếu có ít hơn 6 chữ số thì thêm 0 vào trước đến khi length=6
    }
    public List<Product> findByName(String name){
        return productRepository.findByNameContaining(name);
    }

    public List<String> searchProductIds(String query) {
        try{
            System.out.println(query);
            String url = UriComponentsBuilder.fromHttpUrl("http://127.0.0.1:8000/api/search_ml/")
                    .queryParam("format", "json")
                    .queryParam("query", "{query}")
                    .buildAndExpand(query)
                    .toUriString();
            // Tạo HttpHeaders với Accept header là application/json
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            // Tạo HttpEntity với headers
            HttpEntity<String> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List<String>> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                    new ParameterizedTypeReference<List<String>>() {});
            // Trả về danh sách ID sản phẩm
            return response.getBody();
        } catch (Exception e) {
            System.out.println("Không thể kết nối tới API: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Product> findAllOrderByCreateAtDesc() {
        return productRepository.findAllByOrderByCreateAtDesc();
    }

    public List<Product> findSimilarProducts(String name) {
        String[] keywords = name.split("\\s+"); // Tách các từ bằng dấu cách
        List<Product> resultList = new ArrayList<>(); // Sử dụng List để lưu kết quả

        // Lặp qua từng từ và tìm sản phẩm
        for (String keyword : keywords) {
            System.out.println("Keyword: " + keyword);
            List<Product> products = productRepository.findByNameContainingKeyword(keyword);

            // Duyệt qua danh sách sản phẩm tìm được và thêm vào resultList nếu chưa có sản phẩm nào trùng id
            for (Product product : products) {
                boolean exists = false;
                for (Product p : resultList) {
                    if (p.getId().equals(product.getId())) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    resultList.add(product);
                }
            }
            for (Product p: resultList){
                System.out.println(p.toString());
            }

        }
        // Trả về danh sách sản phẩm không trùng lặp
        return resultList;
    }

//    public List<Product> findAllByIdOrNameOrBrand(String text){
//        return text==null ?
//            (List<Product>) productRepository.findAll() : productRepository.findByIdOrNameOrBrand(text,text);
//    }
    @Autowired
    private OrderItemRepository orderItemRepository;
    public List<Product> getBestSellingProducts(int limit) {
        List<Object[]> results = orderItemRepository.findBestSellingProducts(PageRequest.of(0, limit));
        List<Product> bestSellingProducts = new ArrayList<>();

        for (Object[] result : results) {
            String productId = (String) result[0];
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                bestSellingProducts.add(product);
            }
        }

        return bestSellingProducts;
    }
    public List<Product> getProductsByCategory(String categoryId, int limit) {
        return productRepository.findAllByCategoryId(categoryId, PageRequest.of(0, limit));
    }
    public List<Product> getTopHotProducts() {
        List<Product> hotProducts = productRepository.findTopHotProductsByQuantity();
        return hotProducts.size() > 10 ? hotProducts.subList(0, 10) : hotProducts;  // Limit to 10 products
    }
    public List<Product> getTop10MostRecentProducts() {
        List<Product> recentProducts = productRepository.findTop10MostRecentProducts();
        return recentProducts.size() > 10 ? recentProducts.subList(0, 10) : recentProducts;  // Limit to 10 products
    }

    public List<Product> getPopularProducts() {
        if (orderItemRepository == null) {
            throw new UnsupportedOperationException("OrderItemRepository is not available.");
        }
        return orderItemRepository.findPopularProducts();
    }

    public List<Product> getAllProductsSortedByDate() {
        return productRepository.findAllByOrderByCreateAtDesc(); // Lấy tất cả sản phẩm sắp xếp theo ngày tạo
    }

    public List<Product> getProductsByCategoryTN() {
        return productRepository.findByCategory_Id("TN"); // Lấy các sản phẩm có category_id = "TN"
    }
}
