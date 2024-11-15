package kuit.springbasic.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static kuit.springbasic.util.UserSessionUtils.getUserFromSession;

@Controller
@RequiredArgsConstructor
public class UpdateUserFormController {
    private final UserRepository userRepository;

//    @GetMapping("/user/updateForm")
    public String updateForm(HttpServletRequest request, Model model) {     //Model 객체는 스프링 프레임워크가 자동으로 주입한다
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
}
