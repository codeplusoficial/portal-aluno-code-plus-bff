package br.com.code.plus.project.service;

import br.com.code.plus.project.entity.Student;

public interface StudentInterface {

    Student findStudentById(Integer id);
    Student insertStudent(Student student);
    Student updateStudent(Integer id, Student student);
    void deleteStudent(Integer id);
}
