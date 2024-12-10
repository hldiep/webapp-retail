/*
import { getAuth, RecaptchaVerifier, signInWithPhoneNumber  } from "https://www.gstatic.com/firebasejs/9.3.0/firebase-auth.js";
import { initializeApp } from "https://www.gstatic.com/firebasejs/9.3.0/firebase-app.js";
const firebaseConfig = {
};
const app = initializeApp(firebaseConfig);
const auth = getAuth(app);
auth.languageCode = 'vi';

function initReCapcha(){
    window.recaptchaVerifier = new RecaptchaVerifier('sign-in-button', {
        'size': 'invisible',
        'callback': (response) => {
            sendOTP();
        },
        'expired-callback': () => {
        alert("ReCaptcha đã hết hạn, vui lòng thử lại.");
    }
    }, auth);
}

function sendOTP(){
    console.log(window.recaptchaVerifier);
    const phoneNumber = document.getElementById("phoneNumber").value;
    const appVerifier = window.recaptchaVerifier;
    const auth = getAuth();
    signInWithPhoneNumber(auth, phoneNumber, appVerifier)
        .then((confirmationResult) => {
        // SMS sent.
        // user in with confirmationResult.confirm(code).
        window.confirmationResult = confirmationResult;
        alert("OTP đã được gửi đến số điện thoại của bạn.");
    }).catch((error) => {
        alert("Có lỗi xảy ra khi gửi OTP: " + error.message);
    });
}
window.sendOTP = sendOTP;
window.onload=initReCapcha;*/
