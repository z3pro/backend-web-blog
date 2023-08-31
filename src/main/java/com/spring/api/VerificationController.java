package com.spring.api;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

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
    try {

      if (data == null) {
         return "error-404";

      } else {
        String emailUser = (String) data.get("email");
        model.addAttribute("email", emailUser);
      }
      return "verification";
    } catch (Exception e) {
      return "error-404";
    }
  }

  @RequestMapping(value = "/verification/active", method = { RequestMethod.GET, RequestMethod.POST })
  public ResponseEntity<?> activeUser(@RequestBody(required = false) ActiveUser activeUser) {
    try {

      if (activeUser != null) {
        String email = activeUser.getEmail();
        String otp = activeUser.getOtp();
        UserEntity user = userService.findByEmail(email);
        VerificationEntity verification = verificationService.findByUser(user);
        Date verificationTime = verification.getExpiryDate();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        Date time = new Date(cal.getTime().getTime());
        if (verificationTime.compareTo(time) != -1) {
          if (verification.getVerificationToken().equals(otp)) {
            user.setEnabled(true);
            userService.saveOrUpdate(user);
            verificationService.deleteVerification(verification);
            return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse(("https://hoathinh3d.pro/xem-phim-tinh-than-bien-phan-5/tap-21-sv1.html")));
          } else {
            RedirectView redirect = new RedirectView();
            redirect.setUrl("/error-404");
            return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse(redirect.getUrl()));
          }
        } else {
         RedirectView redirect = new RedirectView();
            redirect.setUrl("/error-404");
            return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse(redirect.getUrl()));
        }
      } else {
        return ResponseEntity.ok(new MessageResponse("https://www.google.com.vn/?hl=vi"));
      }
    } catch (Exception e) {
      System.out.println("error");
      RedirectView redirect = new RedirectView();
      redirect.setUrl("/error-404");
      return ResponseEntity.status(HttpStatus.OK)
          .body(new MessageResponse(redirect.getUrl()));
    }
  }

  @RequestMapping(value = "/error-404", method = { RequestMethod.GET, RequestMethod.POST })
  public String error404() {
    return "error-404";
  }
}
