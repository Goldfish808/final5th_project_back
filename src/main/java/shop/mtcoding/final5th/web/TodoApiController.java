package shop.mtcoding.final5th.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.final5th.config.auth.LoginUser;
import shop.mtcoding.final5th.config.exception.CustomApiException;
import shop.mtcoding.final5th.dto.ResponseDto;
import shop.mtcoding.final5th.dto.TodoReqDto.TodoSaveReqDto;
import shop.mtcoding.final5th.dto.TodoReqDto.TodoUpdateReqDto;
import shop.mtcoding.final5th.dto.TodoRespDto.TodoDetailRespDto;
import shop.mtcoding.final5th.dto.TodoRespDto.TodoListRespDto;
import shop.mtcoding.final5th.dto.TodoRespDto.TodoSaveRespDto;
import shop.mtcoding.final5th.dto.TodoRespDto.TodoUpdateRespDto;
import shop.mtcoding.final5th.service.TodoService;
import shop.mtcoding.final5th.service.UserService;

@RequiredArgsConstructor
@RequestMapping("/s/api")
@RestController
public class TodoApiController {

    private final UserService userService;
    private final TodoService todoService;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final HttpSession session;

    @GetMapping("user/{userId}/todo")
    public ResponseEntity<?> findTodoListByUserId(@PathVariable Long userId) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        if (loginUser.getUserId() != userId) {
            throw new CustomApiException("권한이 없습니다", HttpStatus.FORBIDDEN);
        }
        TodoListRespDto tododListRespDto = todoService.findTodoListByUserId(userId);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "투두 리스트 보기 성공", tododListRespDto),
                HttpStatus.OK);
    }

    @GetMapping("user/{userId}/todo/{todoId}")
    public ResponseEntity<?> findTodoDetail(@PathVariable Long userId, @PathVariable Long todoId) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        if (loginUser.getUserId() != userId) {
            throw new CustomApiException("권한이 없습니다", HttpStatus.FORBIDDEN);
        }
        TodoDetailRespDto todoDetailRespDto = todoService.findTodoDetail(userId, todoId);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "투두 상세보기 성공", todoDetailRespDto),
                HttpStatus.OK);
    }

    @PostMapping("/todo")
    public ResponseEntity<?> saveTodo(@RequestBody TodoSaveReqDto todoSaveReqDto) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new CustomApiException("로그인을 진행해주세요", HttpStatus.FORBIDDEN);
        }
        TodoSaveRespDto todoSaveRespDto = todoService.saveTodo(todoSaveReqDto);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "스케줄 작성 성공", todoSaveRespDto),
                HttpStatus.OK);
    }

    @PutMapping("user/{userId}/todo/{todoId}")
    public ResponseEntity<?> updateTodo(@PathVariable Long userId, @PathVariable Long todoId,
            @RequestBody TodoUpdateReqDto todoUpdateReqDto) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        if (loginUser.getUserId() != userId) {
            throw new CustomApiException("권한이 없습니다", HttpStatus.FORBIDDEN);
        }
        TodoUpdateRespDto todoUpdateRespDto = todoService.updateTodo(userId, todoId,
                todoUpdateReqDto);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "투두 수정 성공", todoUpdateRespDto),
                HttpStatus.OK);
    }

    @DeleteMapping("user/{userId}/todo/{todoId}")
    public ResponseEntity<?> deleteByToId(@PathVariable Long userId, @PathVariable Long todoId) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        if (loginUser.getUserId() != userId) {
            throw new CustomApiException("권한이 없습니다", HttpStatus.FORBIDDEN);
        }
        todoService.deleteByToId(userId, todoId);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "투두 삭제 성공", null),
                HttpStatus.OK);
    }
}
