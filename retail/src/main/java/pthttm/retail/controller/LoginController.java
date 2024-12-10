package pthttm.retail.controller;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pthttm.retail.model.Customer;
import pthttm.retail.security.CustomPasswordEncoder;
import pthttm.retail.service.CustomerService;
import pthttm.retail.service.InfobipSmsService;
import pthttm.retail.service.MailService;
import pthttm.retail.util.OTPSMS;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class LoginController {
    private final MailService mailService;
    private final InfobipSmsService infobipSmsService;
    private final CustomerService customerService;
    private final CustomPasswordEncoder passwordEncoder;
    private Boolean sendPhoneOTP=false;
    private Boolean sendEmailOTP=false;

    private final Map<String, String> phoneOtpStorage = new ConcurrentHashMap<>();
    private final Map<String, String> emailOtpStorage = new ConcurrentHashMap<>();

    @Autowired
    public LoginController(MailService mailService, InfobipSmsService infobipSmsService,
                           CustomerService customerService, CustomPasswordEncoder passwordEncoder) {
        this.mailService = mailService;
        this.infobipSmsService =infobipSmsService;
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/customer/login")
    public String getLogin(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword1 = encoder.encode("12345678@ptithcm");
        System.out.println(encodedPassword1);
        return "login/page-login";
    }

    @GetMapping("/manage/login")
    public String getManageLogin(){
        return "manage/login/page-login";
    }

    @GetMapping("/manage/logout")
    public String getManageLogout(){
        return "redirect:/manage/login";
    }
    @GetMapping("/forgot-password")
    public String getForgotPassword(){
        return "login/page-forgot-password";
    }

    @GetMapping("/manage/forgot-password")
    public String getManageForgotPassword(){
        return "manage/login/page-forgot-password";
    }
    @GetMapping("/confirm/phone")
    public String confirmPhone(){
        return "login/register/page-confirm-phone";
    }

    @PostMapping("/confirm/phone/send-otp")
    @ResponseBody
    public ResponseEntity<String> sendOTP(@RequestParam("phone") String phone) {
        if (phone == null || phone.isEmpty())
            return ResponseEntity.ok("Invalid phone number.");

        if(customerService.existsPhone(phone)) return ResponseEntity.ok("Phone available.");

        String otp = OTPSMS.randomOTP();
        phoneOtpStorage.put(phone, otp); // Store OTP for the phone number
        try {
            infobipSmsService.sendSMS(phone, "Mã OTP của bạn là: " + otp);
            sendPhoneOTP = true;
            return ResponseEntity.ok("OTP đã được gửi đến số " + phone);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể gửi OTP. Vui lòng thử lại.");
        }
    }

    @PostMapping("/confirm/email")
    public String verifyOtp(@RequestParam("phone") String phone, @RequestParam("verify-code") String otp,
                            HttpSession session, Model model) {
        if (!sendPhoneOTP) {
            model.addAttribute("message", "Vui lòng xác thực số điện thoại!");
            return "redirect:/confirm/phone";
        }
        String savedOtp = phoneOtpStorage.get(phone);
        if (savedOtp != null && savedOtp.equals(otp)) {
            model.addAttribute("message", "OTP verified successfully!");
            session.setAttribute("phone",phone);
            return "login/register/page-confirm-email"; // Proceed to the next page
        } else {
            model.addAttribute("message", "OTP không hợp lệ. Hãy thử lại.");
//          return "/login/register/page-confirm-phone";

            session.setAttribute("phone",phone);
            return "login/register/page-confirm-email";
        }
    }

    @PostMapping("/confirm/email/send-otp")
    @ResponseBody
    public ResponseEntity<String> sendMailOTP(@RequestParam("email") String email) {
        if (email == null || email.isEmpty())
            return ResponseEntity.ok("Email không hợp lệ!");

        Customer customer;
        if (customerService.existsEmail(email)) return ResponseEntity.ok("Email đã tồn tại!");

        String otp = OTPSMS.randomOTP();
        emailOtpStorage.put(email, otp); // Store OTP for the phone number
        sendEmailOTP=true;
        mailService.sendMail(email,"Fruit Shop - Confirm OTP","Your OTP is: " + otp);

        return ResponseEntity.ok("OTP sent to " + email);
    }

    @PostMapping("/register")
    public  String getRegister(Model model, HttpSession session,
                               @RequestParam("email") String email, @RequestParam("verify-code") String otp){
        if (!sendEmailOTP) {
            model.addAttribute("message", "Vui lòng nhấn xác thực email!");
            return "redirect:/confirm/email";
        }
        String savedOtp = emailOtpStorage.get(email);
        if (savedOtp != null && savedOtp.equals(otp)) {
            model.addAttribute("message", "OTP verified successfully!");
            session.setAttribute("email",email);
            model.addAttribute("customer", new Customer());
            return "login/register/page-register"; // Proceed to the next page
        } else {
            model.addAttribute("message", "OTP không hợp lệ. Hãy thử lại.");
//          return "/login/register/page-confirm-email";

            model.addAttribute("customer", new Customer());
            session.setAttribute("email",email);
            return "login/register/page-register";
        }
    }
    @PostMapping("/register/confirm")
    @ResponseBody
    public ResponseEntity<String> confirmRegister( HttpSession session,
            @RequestParam("confirm-password") String confirmPassword,
            @ModelAttribute Customer customer) {
        String phone = (String) session.getAttribute("phone");
        String email = (String) session.getAttribute("email");
        if (phone == null || email == null) {
            return ResponseEntity.ok("Phone or email not found in session!");
        }
        if (customer.getLastName().isEmpty()||customer.getLastName().isBlank()) {
            return ResponseEntity.ok("Invalid name!");
        }
        if (customer.getAddress().isEmpty()||customer.getAddress().isBlank()) {
            return ResponseEntity.ok("Invalid address!");
        }
        if (customer.getPassword().isEmpty()||customer.getPassword().isBlank()) {
            return ResponseEntity.ok("Invalid password!");
        }
        if (confirmPassword.isEmpty()||!confirmPassword.equals(customer.getPassword())) {
            return ResponseEntity.ok("Invalid confirm password!");
        }
        customer.processName(customer.getLastName());
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerService.addCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Registration-Status", "Successful");
        // Trả về phản hồi với header và body thông báo thành công
        return new ResponseEntity<>("Đăng ký thành công. Vui lòng đăng nhập.", headers, HttpStatus.FOUND);
    }


}
