package com.mcosta.validator;

import com.mcosta.dao.ReaderDao;
import com.mcosta.model.Reader;
import com.mcosta.model.Student;
import com.mcosta.model.Teacher;
import com.mcosta.util.ValidatorCPF;

public class ReaderValidator {

    private ReaderDao readerDao = new ReaderDao();

    public void isValid(Teacher teacher) throws Exception {
        isValid(teacher, true);
    }

    public void isValid(Teacher teacher, boolean isCreating) throws Exception {
        isValidReader(teacher);
        if (teacher.getDiscipline() == null || teacher.getDiscipline().isEmpty()) {
            throw new Exception("Disciplina não informada.");
        }
    }

    public void isValid(Student student) throws Exception {
        isValid(student, true);
    }

    public void isValid(Student student, boolean isCreating) throws Exception {
        isValidReader(student);
        if (student.getRegistration() == null || student.getRegistration().isEmpty()) {
            throw new Exception("Matrícula não informada.");
        }
    }

    private void isValidReader(Reader reader) throws Exception {
        if (reader.getCpf() == null || reader.getCpf().isEmpty()) {
            throw new Exception("CPF não informado.");
        }

        if(ValidatorCPF.isNotValid(reader.getCpf())){
            throw new Exception("CPF inválido");
        }

        if (reader.getName() == null || reader.getName().isEmpty()) {
            throw new Exception("Nome não informado.");
        }

        Reader r = readerDao.findByCpf(reader.getCpf());
        if(r != null){
            throw new Exception("CPF já utilizado.");
        }
    }


}
