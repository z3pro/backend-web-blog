package com.spring.api;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.entity.UserEntity;
import com.spring.entity.VerificationEntity;
import com.spring.payload.request.ActiveUser;
import com.spring.payload.response.MessageResponse;
import com.spring.service.impl.UserService;
import com.spring.service.impl.VerificationService;

@Controller
public class VerificationController {
  @Autowired
  private UserService userService;
  @Autowired
  private VerificationService verificationService;

  @RequestMapping(value = "/verification", method = { RequestMethod.GET, RequestMethod.POST })
  public String redirectVerification(Model model, @RequestBody(required = false) Map<String, Object> data) {
    if (data == null) {
      // return "error-404";
      model.addAttribute("email", "tiendat.dev203@gmail.com");

    } else {
      System.out.println(data.get("email"));
      String emailUser = (String) data.get("email");
      model.addAttribute("email", emailUser);
    }
    return "verification";
  }

  @RequestMapping(value = "/verification/active", method = { RequestMethod.GET, RequestMethod.POST })
  public ResponseEntity<?> activeUser(@RequestBody(required = false) ActiveUser activeUser) {
    if (activeUser != null) {
      String email = activeUser.getEmail();
      String otp = activeUser.getOtp();
      UserEntity user = userService.findByEmail(email);
      VerificationEntity verification = verificationService.findByUser(user);
      Date verificationTime = verification.getExpiryDate();
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(new Date().getTime());
      Date time = new Date(cal.getTime().getTime());
      if (verificationTime.compareTo(time) >= 0) {
        if (verification.getVerificationToken() == otp) {
          user.setEnabled(true);
          userService.saveOrUpdate(user);
          return ResponseEntity.ok(new MessageResponse("https://www.google.com.vn/?hl=vi"));
        }
      }
      ;
    }
    return ResponseEntity.ok(new MessageResponse("https://www.google.com.vn/?hl=vi"));
  }

  @GetMapping("/test")
  public String test() {
    return "redirect:/verification";
  }
}
