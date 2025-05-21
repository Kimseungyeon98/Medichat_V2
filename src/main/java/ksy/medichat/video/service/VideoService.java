package ksy.medichat.video.service;

import ksy.medichat.video.vo.VideoVO;

import java.util.List;
import java.util.Map;


public interface VideoService {
	public void insertV(VideoVO vo);
	public void updateV(VideoVO vo);
	public void deleteV(Long video_num);
	public Integer selectCountV(Map<String,Object> map);
	public  VideoVO selectV(Long video_num);
	public List<VideoVO> selectVList(Map<String,Object> map);
	public void updateVhit(Long video_num);
}
