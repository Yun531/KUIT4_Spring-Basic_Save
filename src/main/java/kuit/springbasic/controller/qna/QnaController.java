package kuit.springbasic.controller.qna;

import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QnaController {
    /**
     * showQnA
     */
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @GetMapping("/qna/show")
    public String showQna(@RequestParam("questionId") String questionId, Model model){
        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));
        List<Answer> answers = (List<Answer>)answerRepository.findAllByQuestionId(question.getQuestionId());

        model.addAttribute("question", question);
        model.addAttribute("answers", answers);

        return "/qna/show";
    }
}
