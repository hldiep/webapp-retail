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

document.getElementById("sign-in-button").onclick = function() {
    const email = document.getElementById("email").value;
    const messageDiv = document.getElementById("register__message");
    const sendButton = document.getElementById("sign-in-button");
    if (!email) {
        messageDiv.innerText = "Vui lòng nhập số điện thoại.";
        messageDiv.style.color = "red";
        return;
    }
    openModal("Đang gửi OTP...");
    sendButton.disabled = true;

    fetch("/confirm/email/send-otp", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: "email=" + encodeURIComponent(email)
    })
        .then(response => response.text())
        .then(data => {
//        openModal(data);
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
};

document.getElementById('verify-code').addEventListener('input', function () {
    const submitButton = document.getElementById('submit-button');
    if (this.value.trim() === "") {
        alert("Vui lòng nhập mã OTP trước khi xác thực.");
    } else {
        submitButton.disabled = false; // Bật nút nếu có ký tự
    }
});

window.onload = function() {
    loadModal();
};