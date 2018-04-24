package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.VideoDao;
import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoServiceImpl implements VideoService {

  private VideoDao videoDao;

  @Autowired
  public VideoServiceImpl(VideoDao videoDao) {
    this.videoDao = videoDao;
  }

  @Override
  @Transactional(readOnly = false)
  public Video add(String url, Course course) {
    Video video = new Video();

    video.setUrl(url);
    video.setCourse(course);

    videoDao.add(video);

    return video;
  }

  @Override
  @Transactional(readOnly = true)
  public Video ofId(Integer id) {
    return videoDao.ofId(id);
  }
}
