package com.kou.rollcall.repositories;

import com.kou.rollcall.model.Announcement;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnnouncementRepository extends CrudRepository<Announcement, Long>
{
    List<Announcement> getAnnouncementsByLesson_Id(Long id);
}
