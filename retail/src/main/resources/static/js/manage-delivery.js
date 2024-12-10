function filterData() {
    let filterShipValue = document.querySelector('input[name="filterShipStatus"]:checked').value;
    let filterPayValue = document.querySelector('input[name="filterPayStatus"]:checked').value;
    let rows = document.querySelectorAll('.invoice-table tbody tr');

    rows.forEach(row => {
        let shipSelect = row.querySelector('td:nth-child(7) select'); // Thẻ select trong cột trạng thái giao hàng
        let paySelect = row.querySelector('td:nth-child(6) select');  // Thẻ select trong cột trạng thái thanh toán

        let shipStatus = shipSelect.value.trim(); // Giá trị từ thẻ select trạng thái giao hàng
        let payStatus = paySelect.value.trim();  // Giá trị từ thẻ select trạng thái thanh toán

        // So khớp với các giá trị lọc
        let shipMatch = filterShipValue === 'ALL' ||
        (filterShipValue === 'CB' && shipStatus === 'CB') ||
        (filterShipValue === 'DG' && shipStatus === 'DG') ||
        (filterShipValue === 'HT' && shipStatus === 'HT');

        let payMatch = filterPayValue === 'ALL' ||
        (filterPayValue === 'HT' && payStatus === 'HT') ||
        (filterPayValue === 'CH' && payStatus === 'CH');

        // Hiển thị hoặc ẩn hàng dựa trên điều kiện khớp
        if (shipMatch && payMatch) {
            row.style.display = ''; // Hiển thị hàng nếu cả hai điều kiện khớp
        } else {
            row.style.display = 'none'; // Ẩn hàng nếu không khớp
        }
    });
}
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
            filterData();
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
            filterData();
            alert('Cập nhật trạng thái giao hàng thành công!');
        } else {
            alert('Cập nhật thất bại, vui lòng thử lại.');
        }
    })
        .catch(error => console.error('Error:', error));
}

function redirectToOrder(element) {
    var productId = element.getAttribute('data-id');
    window.location.href = '/manage/delivery/' + productId;
}