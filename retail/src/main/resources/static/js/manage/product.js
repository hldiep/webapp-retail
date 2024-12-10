function redirectToProduct(element) {
    var productId = element.getAttribute('data-id');
    window.location.href = '/manage/edit-product/' + productId;
}

function submitCombinedForms() {
    // Thu thập dữ liệu từ form tìm kiếm
    const search = document.getElementById('search-product').value;
    // Thu thập dữ liệu từ form lọc
    const category = document.getElementById('sort-product').value;

    // Tạo URL với các tham số tìm kiếm và lọc
    const params = new URLSearchParams();
    if (category) params.append('sort-product', category);
    if (search) params.append('search-product', search);


    // Chuyển hướng tới endpoint với các tham số
    window.location.href = `/manage/product?${params.toString()}`;
}