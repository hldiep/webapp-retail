function loadModal() {
    fetch('/modal/popup_modal.html')
        .then(response => response.text())
        .then(data => {
        // Chèn modal vào cuối body
        document.body.insertAdjacentHTML('beforeend', data);
        // Hiển thị modal
        const modal = document.getElementById("otpModal");
        modal.style.display = "block";
        closeModal();
    })
        .catch(error => {
        console.error('Error loading modal:', error);
    });
}
function openModal(message) {
    const modal = document.getElementById("otpModal");
    const messageDiv = document.getElementById("otpMessage");
    messageDiv.innerText = message;
    modal.style.display = "block";  // Hiển thị modal
}
function closeModal() {
    const modal = document.getElementById("otpModal");
    modal.style.display = "none";  // Ẩn modal
}

function sendOtp() {
    const phone = document.getElementById("phone").value;
    const messageDiv = document.getElementById("register__message");
    const sendButton = document.getElementById("sign-in-button");
    //thong bao đang gửi OTP
    if (!phone) {
        messageDiv.innerText = "Vui lòng nhập số điện thoại.";
        messageDiv.style.color = "red";
        return;
    }

    openModal("Đang gửi OTP...");
    sendButton.disabled = true;
    fetch("/confirm/phone/send-otp", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: "phone=" + encodeURIComponent(phone)
    })
        .then(response => response.text())
        .then(data => {
//            openModal(data);
            messageDiv.innerText = data;
//            closeModal();
            if (data.includes("OTP đã được gửi")) {
                messageDiv.style.color = "green"; // Thông báo thành công
            } else {
                messageDiv.style.color = "red"; // Thông báo lỗi
            }
    })
        .catch(error => {
        console.error('Error:', error);
        openModal("Có lỗi xảy ra khi gửi OTP. Vui lòng thử lại.");
    }).finally(() => {
        // Bật lại nút gửi sau khi hoàn tất
        sendButton.disabled = false;
        closeModal();
    });
}

// Attach the function to the button's onclick event
document.getElementById("sign-in-button").onclick = sendOtp;
window.onload = function() {
    loadModal();
};

document.getElementById('verify-code').addEventListener('input', function () {
    const submitButton = document.getElementById('submit-button');
    if (this.value.trim() === "") {
        alert("Vui lòng nhấn xác thực.");
    } else {
        submitButton.disabled = false; // Bật nút nếu có ký tự
    }
});