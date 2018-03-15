package com.kou.rollcall.repositories;

import com.kou.rollcall.model.Academician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AcademicianRepository extends CrudRepository<Academician, Long>, JpaRepository<Academician, Long>
{
    Academician getAcademicianByName(String name);

    Academician getAcademicianByUsername(String username);

    Academician getAcademicianByUsernameAndPassword(String username, String password);
}
