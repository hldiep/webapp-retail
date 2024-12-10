
function updatePayStatus(selectElement) {
    if (!selectElement) {
        console.error("Không tìm thấy phần tử select");
        return;
    }

    var orderId = selectElement.getAttribute('data-id');
    let newPayStatus = selectElement.value;
    console.log("Order ID:", orderId);
    console.log("New Payment Status:", newPayStatus);

    // Lấy CSRF token từ thẻ meta
    //    var csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    //    var csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    // Validate orderId và newPayStatus
    if (!orderId || !newPayStatus) {
        alert('Invalid order ID or payment status.');
        return;
    }
    // Gửi yêu cầu AJAX để cập nhật trạng thái thanh toán
    //    fetch(`/manage/update-payment-status/${orderId}`, {
    //        method: 'POST',
    //        headers: {
    //            'Content-Type': 'application/json',
    //            [csrfHeader]: csrfToken // Thêm token CSRF vào header
    //        },
    //        body: JSON.stringify({ payStatus: newPayStatus }),
    //    })
    fetch(`/manage/update-payment-status/${orderId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ payStatus: newPayStatus }),
    })
        .then(response => response.json())
        .then(data => {
        if (data.success) {
            selectElement.value = newPayStatus;
            alert('Cập nhật trạng thái thanh toán thành công!');
        } else {
            alert('Cập nhật thất bại, vui lòng thử lại.');
        }
    })
        .catch(error => console.error('Error:', error));
}

function updateShipStatus(selectElement) {
    if (!selectElement) {
        console.error("Không tìm thấy phần tử select");
        return;
    }
    var orderId = selectElement.getAttribute('data-id');
    let newShipStatus = selectElement.value;

    console.log("Order ID:", orderId);
    console.log("New Payment Status:", newShipStatus);
    // Gửi yêu cầu AJAX để cập nhật trạng thái giao hàng
    fetch(`/manage/update-shipping-status/${orderId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ shipStatus: newShipStatus }),
    })
        .then(response => response.json())
        .then(data => {
        if (data.success) {
            selectElement.value = newShipStatus;
            alert('Cập nhật trạng thái giao hàng thành công!');
        } else {
            alert('Cập nhật thất bại, vui lòng thử lại.');
        }
    })
        .catch(error => console.error('Error:', error));
}

function redirectToProduct(element) {
    var productId = element.getAttribute('data-id');
    window.location.href = '/manage/edit-product/' + productId;
}