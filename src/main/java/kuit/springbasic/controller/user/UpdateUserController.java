package kuit.springbasic.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UpdateUserController {
    private final UserRepository userRepository;

//    @RequestMapping("/user/update")
    public String updateUser(@ModelAttribute User user, HttpServletRequest request){
        userRepository.update(user);

        HttpSession session = request.getSession();

        session.removeAttribute(USER_SESSION_KEY);
        session.setAttribute(USER_SESSION_KEY, user);

        return "redirect:/user/list";
    }

}
