package com.talentboard.talentboard.repositories;

import com.talentboard.talentboard.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // Para validar la regla: Un candidato no podrá postularse más de una vez a la misma vacante
    boolean existsByCandidateIdAndVacancyId(Long candidateId, Long vacancyId);

    // Para filtrar el historial específico de un candidato
    List<Application> findByCandidateId(Long candidateId);
}