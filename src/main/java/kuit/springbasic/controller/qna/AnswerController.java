package kuit.springbasic.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/qna")
public class AnswerController {
    /**
     * addAnswerV0 : @RequestParam, HttpServletResponse
     * addAnswerV1 : @RequestParam, Model
     * addAnswerV2 : @RequestParam, @ResponseBody
     * addAnswerV3 : @ModelAttribute, @ResponseBody
     */
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
//    @PostMapping("/addAnswer")
    public void addAnswerV0(@RequestParam int questionId,
                            @RequestParam String writer,
                            @RequestParam String contents,
                            HttpServletResponse response) throws SQLException, IOException {

        Answer answer = new Answer(questionId, writer, contents);
        Answer savedAnswer = answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(questionId);
        question.increaseCountOfAnswer();

        Map<String, Object> model = new HashMap<>();
        model.put("answer", savedAnswer);

        //JSON 응답을 보내기 위한 부분(기존의 jsonView() 에서 처리해주던 부분
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(model));
    }

//    @PostMapping("/addAnswer")
    public String addAnswerV1(@RequestParam int questionId,
                            @RequestParam String writer,
                            @RequestParam String contents,
                            Model model){

        Answer answer = new Answer(questionId, writer, contents);
        Answer savedAnswer = answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(questionId);
        question.increaseCountOfAnswer();

        model.addAttribute("answer", savedAnswer);

        return "jsonView";          //해당 View가 없는거 아닌가? 모르겠는데
    }

//    @PostMapping("/addAnswer")
    @ResponseBody
    public Answer addAnswerV2(@RequestParam int questionId,
                              @RequestParam String writer,
                              @RequestParam String contents) {

        Answer answer = new Answer(questionId, writer, contents);

        Question question = questionRepository.findByQuestionId(questionId);
        question.increaseCountOfAnswer();

        return answerRepository.insert(answer);
        //answers 라는 Map을 반환해줌 >> 해당 HashMap 이 JSON으로 변환되고, HTTP 응답 본문에 포함
    }

    @PostMapping("/addAnswer")
    @ResponseBody
    public Answer addAnswerV3(@ModelAttribute Answer answer) {
        Question question = questionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();

        return answerRepository.insert(answer);
    }
}
