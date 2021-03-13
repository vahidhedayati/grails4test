package tutor

import uni.tutor.Tutor

interface TutorInterfaceService {
    Tutor get(Long id)
    Tutor save(Tutor tutor)
    void delete(Long id)
    Long count()
    List<Tutor> getTutors(Map args)
}
