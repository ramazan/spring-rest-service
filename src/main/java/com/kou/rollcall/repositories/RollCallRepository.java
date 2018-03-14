package com.kou.rollcall.repositories;

import com.kou.rollcall.model.RollCall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RollCallRepository extends JpaRepository<RollCall, Long>
{
    List<RollCall> getRollCallByStudent_IdAndLesson_Id(long studentId, long lessonId);
}
