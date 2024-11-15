package kuit.springbasic.controller.user;

import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CreateUserController {
    private final UserRepository userRepository;

//    @PostMapping("/user/signup")
    public String createUser(@ModelAttribute User user) {
        userRepository.insert(user);

        return "redirect:/user/list";
    }
}