package ksy.medichat.video.service;

import ksy.medichat.video.dao.VideoMapper;
import ksy.medichat.video.vo.VideoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VideoServiceImpl implements VideoService{

	@Autowired
	private VideoMapper mapper;
	@Override
	public void insertV(VideoVO vo) {
		// TODO Auto-generated method stub
		mapper.insertV(vo);
	}

	@Override
	public void updateV(VideoVO vo) {
		// TODO Auto-generated method stub
		mapper.updateV(vo);
	}

	@Override
	public void deleteV(Long video_num) {
		// TODO Auto-generated method stub
		mapper.deleteV(video_num);
	}

	@Override
	public Integer selectCountV(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectCountV(map);
	}

	@Override
	public VideoVO selectV(Long video_num) {
		// TODO Auto-generated method stub
		return mapper.selectV(video_num);
	}

	@Override
	public List<VideoVO> selectVList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectVList(map);
	}

	@Override
	public void updateVhit(Long video_num) {
		// TODO Auto-generated method stub
		mapper.updateVhit(video_num);
	}

}
