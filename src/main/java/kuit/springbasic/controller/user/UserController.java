package kuit.springbasic.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;
import static kuit.springbasic.util.UserSessionUtils.getUserFromSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    @GetMapping("/form")
    public String showUserForm() {
        log.info("showForm");

        return "/user/form";
    }


    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
//    @PostMapping("/user/signup")
    public String createUserV1(@RequestParam String userId,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email) {

        User user = new User(userId, password, name, email);
        userRepository.insert(user);

        return "redirect:/user/list";
    }
    @PostMapping("/user/signup")
    public String createUserV2(@ModelAttribute User user) {
        userRepository.insert(user);

        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String showUserList(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        if (UserSessionUtils.isLoggedIn(session)) {
            model.addAttribute("users", userRepository.findAll());

            return "/user/list";
        }

        return "redirect:/user/loginForm";
    }
    @GetMapping("/updateForm")
    public String updateUserForm(HttpServletRequest request, Model model) {     //Model 객체는 스프링 프레임워크가 자동으로 주입한다
        String userId = request.getParameter("userId");
        User user = userRepository.findByUserId(userId);
        HttpSession session = request.getSession();

        if (user != null) {
            if (user.isSameUser(getUserFromSession(session))) {
                model.addAttribute("user", user);

                return "/user/updateForm";
//                메서드에서 return "/user/updateForm";을 반환하면, 뷰 리졸버(View Resolver)가 이를 JSP 파일 경로로 해석한다.
            }
        }

        return "redirect:/";
    }

    /**
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
//    @RequestMapping("/update")
    public String updateUserV1(@RequestParam String userId,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email,
                               HttpServletRequest request) {

        User user = new User(userId, password, name, email);
        userRepository.update(user);

        HttpSession session = request.getSession();

        session.removeAttribute(USER_SESSION_KEY);
        session.setAttribute(USER_SESSION_KEY, user);

        return "redirect:/user/list";
    }
    @RequestMapping("/update")
    public String updateUserV2(@ModelAttribute User user, HttpServletRequest request){
        userRepository.update(user);

        HttpSession session = request.getSession();

        session.removeAttribute(USER_SESSION_KEY);
        session.setAttribute(USER_SESSION_KEY, user);

        return "redirect:/user/list";
    }

}
