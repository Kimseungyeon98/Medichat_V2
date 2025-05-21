package ksy.medichat.reservation.service;

import ksy.medichat.doctor.vo.DoctorVO;
import ksy.medichat.hospital.vo.HospitalVO;
import ksy.medichat.reservation.vo.ReservationVO;

import java.util.List;
import java.util.Map;

public interface ReservationService {
	//병원진료시간 가져오기
	public HospitalVO getHosHours(Long hos_num);
	//특정 날짜와 시간에 근무하는 의사 가져오기
	public List<DoctorVO> getAvailableDoctors(Map<String, Object> params);
	//예약정보 저장하기
    public void insertReservation(ReservationVO reservationVO);
    //예약내역 가져오기
    public Integer selectCountByMem(long mem_num);
    public List<ReservationVO> getMyResList(Map<String,Object> map);
    //예약내역 삭제하기
    public void cancelReservation(Long res_num);
    //예약 내역 가져오기(의사)
    public Integer selectCountByDoc(Map<String,Object> map);
    public List<ReservationVO> getDocResList(Map<String,Object> map);
    //예약내역 업데이트 (0:예약승인대기, 1:진료예정, 2:진료완료, 3:예약취소)
    public void updateReservation(Long res_num,Long res_status);
    //예약내역 존재 여부
    public List<String> getResExist(Map<String,Object> map);
    ReservationVO getReservationById(long res_num);
	//예약 번호를 기반으로 의사 번호 가져오기
	public long selectDoc_num(long res_num);
	//진료 완료된 내역 가져오기
	public Integer selectCountByCompleted(Map<String,Object> map);
	public List<ReservationVO> getDocCompletedList(Map<String,Object> map);
	//마이페이지 캘린더용 예약내역 가져오기
    public List<String> getReservationsByMember(Long mem_num);
}
