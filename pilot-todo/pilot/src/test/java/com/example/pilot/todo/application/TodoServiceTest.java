package com.example.pilot.todo.application;

import com.example.pilot.TestEntityFactory;
import com.example.pilot.todo.application.dto.request.TodoCreateRequestDto;
import com.example.pilot.todo.application.dto.request.TodoUpdateRequestDto;
import com.example.pilot.todo.application.dto.response.TodoCreateResponseDto;
import com.example.pilot.todo.application.dto.response.TodoDeleteResponseDto;
import com.example.pilot.todo.application.dto.response.TodoStatusChangeResponseDto;
import com.example.pilot.todo.domain.Todo;
import com.example.pilot.todo.domain.TodoStatus;
import com.example.pilot.todo.domain.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;
    @InjectMocks
    private TodoService todoService;
    private Todo todo;

    @BeforeEach
    void setUp() {
        todo = TestEntityFactory.getTodoInstance();
    }

    @Test
    void 새로운_일정_저장_테스트() throws Exception{
        //given
        TodoCreateRequestDto todoCreateRequestDto = new TodoCreateRequestDto("test");
        given(todoRepository.save(any(Todo.class))).willReturn(todo);

        //when
        TodoCreateResponseDto todoCreateResponseDto = todoService.save(todoCreateRequestDto);

        //then
        assertThat(todoCreateResponseDto.getId()).isGreaterThan(0L);
        assertThat(todoCreateResponseDto.getText()).isNotBlank();
    }

    @Test
    void Todo_내용_변경_테스트() throws Exception{
        //given
        TodoUpdateRequestDto todoUpdateRequestDto = TodoUpdateRequestDto.builder()
                .todoId(todo.getId())
                .text("new text")
                .build();
        given(todoRepository.findById(todo.getId())).willReturn(Optional.ofNullable(todo));
        String beforeText = todo.getText();

        //when
        todoService.update(todoUpdateRequestDto);
        String afterText = todo.getText();

        //then
        assertThat(beforeText).isNotEqualTo(afterText);
    }

    @Test
    void 단건_완료_활성화_테스트() throws Exception{
        //given
        given(todoRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(todo));

        //when
        todoService.changeStatus(todo.getId());

        //then
        assertThat(todo.getStatus()).isEqualTo(TodoStatus.COMPLETE);
    }

    @Test
    void 전체_완료_활성화_테스트() throws Exception{
        //given
        given(todoRepository.updateAllStatus(any(TodoStatus.class))).willReturn(10L);

        //when
        TodoStatusChangeResponseDto responseDto = todoService.changeAllTodoStatus(TodoStatus.ACTIVE);

        //then
        assertThat(responseDto.getUpdateCount()).isEqualTo(10);
    }

    @Test
    void 단건_Todo_제거_테스트() throws Exception{
        //given

        //when
        todoService.delete(todo.getId());

        //then
        verify(todoRepository).delete(any(Long.class));
    }

    @Test
    void 완료건_제거_테스트() throws Exception{
        //given
        given(todoRepository.deleteAllComplete()).willReturn(3L);

        //when
        TodoDeleteResponseDto todoDeleteResponseDto = todoService.deleteComplete();

        //then
        assertThat(todoDeleteResponseDto.getDeleteCount()).isEqualTo(3);
    }
}