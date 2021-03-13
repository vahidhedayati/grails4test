package student


import uni.student.Student

interface StudentInterfaceService {
    Student get(Long id)
    Student save(Student student)
    void delete(Long id)
    Long count()
    List<Student> getStudents(Map args)
}
