package br.com.code.plus.project.controller;

import br.com.code.plus.project.entity.Student;
import br.com.code.plus.project.service.impl.StudentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/aluno")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ApiOperation(value = "Responsible for searching the student through the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return student by id"),
            @ApiResponse(code = 401, message = "You do not have permission to access this feature."),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Student> findAlunoById(@PathVariable Integer id) {
        Student student = studentService.findStudentById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @ApiOperation(value = "Responsible for creating students")
    @PostMapping
    public ResponseEntity<Student> createAluno(@RequestBody Student student) {
        Student response = studentService.insertStudent(student);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Responsible for updating students")
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateAluno(@PathVariable Integer id, @RequestBody Student student) {
        Student response = studentService.updateStudent(id, student);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Responsible for deleting students")
    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteAluno(@PathVariable Integer id){
       studentService.deleteStudent(id);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
