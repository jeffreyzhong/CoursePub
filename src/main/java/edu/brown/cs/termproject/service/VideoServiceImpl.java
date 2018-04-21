package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.VideoDao;
import edu.brown.cs.termproject.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService {

  private VideoDao videoDao;

  @Autowired
  public VideoServiceImpl(VideoDao videoDao) {
    this.videoDao = videoDao;
  }

  @Override
  public void add(Video video) {
    videoDao.add(video);
  }
}
