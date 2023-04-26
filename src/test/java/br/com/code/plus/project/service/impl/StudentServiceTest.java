package br.com.code.plus.project.service.impl;

import br.com.code.plus.project.entity.Student;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.BDDAssertions.then;


@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {

    @Mock
    private StudentService studentService; //null

    @Test
    public void shouldCreateStudent() {
        var request = new Student();
        request.setUserName("user-name");
        request.setName("name");
        request.setEmail("email");
        request.setPhone("phone");

        given(studentService.insertStudent(request)).willReturn(request);

        var result = studentService.insertStudent(request);

        then(request.getName()).isEqualTo(result.getName());
        then(request.getEmail()).isEqualTo(result.getEmail());
        then(request.getPassword()).isEqualTo(result.getPassword());
        then(request.getPhone()).isEqualTo(result.getPhone());
        then(request.getUserName()).isEqualTo(result.getUserName());
    }

    @Test
    public void shouldReturnStudentById() {
        var request = new Student();
        request.setId(1234);
        request.setUserName("user-name");
        request.setName("name");
        request.setEmail("email");
        request.setPhone("phone");

        given(studentService.findStudentById(request.getId())).willReturn(request);

        var result = studentService.findStudentById(request.getId());

        then(request.getName()).isEqualTo(result.getName());
        then(request.getEmail()).isEqualTo(result.getEmail());
        then(request.getPassword()).isEqualTo(result.getPassword());
        then(request.getPhone()).isEqualTo(result.getPhone());
        then(request.getUserName()).isEqualTo(result.getUserName());
    }

    @Test
    public void shouldUpdateStudent() {
        var request = new Student();
        request.setId(1234);
        request.setUserName("user-name");
        request.setName("name");
        request.setEmail("email");
        request.setPhone("phone");

        given(studentService.updateStudent(request.getId(), request)).willReturn(request);

        var result = studentService.updateStudent(request.getId(), request);

        then(request.getName()).isEqualTo(result.getName());
        then(request.getEmail()).isEqualTo(result.getEmail());
        then(request.getPassword()).isEqualTo(result.getPassword());
        then(request.getPhone()).isEqualTo(result.getPhone());
        then(request.getUserName()).isEqualTo(result.getUserName());
    }
}