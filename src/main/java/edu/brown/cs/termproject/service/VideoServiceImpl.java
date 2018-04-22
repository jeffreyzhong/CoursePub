package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.VideoDao;
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
  public void add(Video video) {
    videoDao.add(video);
  }
}
