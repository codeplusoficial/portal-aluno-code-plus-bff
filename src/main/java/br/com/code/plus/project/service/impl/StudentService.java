package br.com.code.plus.project.service.impl;

import br.com.code.plus.project.entity.Student;
import br.com.code.plus.project.repository.StudentRepository;
import br.com.code.plus.project.service.StudentInterface;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class StudentService implements StudentInterface {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    @Override
    public Student findStudentById(Integer id) {
        Student response = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado na base de dados"));

        return response;
    }

    @Override
    public Student insertStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Integer id, Student student) {
        Student response = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado na base de dados, não foi possivel atualizar"));

        response.setName(student.getName());
        response.setPhone(student.getPhone());
        response.setEmail(student.getEmail());
        response.setPassword(student.getPassword());
        response.setUserName(response.getUserName());

        return studentRepository.save(response);

    }
    @Override
    public void deleteStudent(Integer valor) {
        studentRepository.deleteById(valor);
    }
}
