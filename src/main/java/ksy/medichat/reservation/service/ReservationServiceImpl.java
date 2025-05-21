package ksy.medichat.reservation.service;

import ksy.medichat.doctor.vo.DoctorVO;
import ksy.medichat.hospital.vo.HospitalVO;
import ksy.medichat.reservation.dao.ReservationMapper;
import ksy.medichat.reservation.vo.ReservationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService{

	@Autowired
	private ReservationMapper reservationMapper;
	
	@Override
	public HospitalVO getHosHours(Long hos_num) {
		return reservationMapper.getHosHours(hos_num);
	}

	@Override
	public List<DoctorVO> getAvailableDoctors(Map<String, Object> params) {
		return reservationMapper.getAvailableDoctors(params);
	}

	@Override
    public void insertReservation(ReservationVO reservation) {
        reservationMapper.insertReservation(reservation);
    }

	@Override
	public Integer selectCountByMem(long mem_num) {
		return reservationMapper.selectCountByMem(mem_num);
	}

	@Override
	public List<ReservationVO> getMyResList(Map<String, Object> map) {
		return reservationMapper.getMyResList(map);
	}

	@Override
	public void cancelReservation(Long res_num) {
		reservationMapper.cancelReservation(res_num);
	}

	@Override
	public Integer selectCountByDoc(Map<String, Object> map) {
		return reservationMapper.selectCountByDoc(map);
	}

	@Override
	public List<ReservationVO> getDocResList(Map<String, Object> map) {
		return reservationMapper.getDocResList(map);
	}

	@Override
	public void updateReservation(Long res_num,Long res_status) {
		reservationMapper.updateReservation(res_num,res_status);
	}

	@Override
	public List<String> getResExist(Map<String, Object> map) {
		return reservationMapper.getResExist(map);
	}

	@Override
	public ReservationVO getReservationById(long res_num) {
		return reservationMapper.getReservationById(res_num);
	}

	@Override
	public long selectDoc_num(long res_num) {
		return reservationMapper.selectDoc_num(res_num);
	}

	@Override
	public Integer selectCountByCompleted(Map<String, Object> map) {
		return reservationMapper.selectCountByCompleted(map);
	}

	@Override
	public List<ReservationVO> getDocCompletedList(Map<String, Object> map) {
		return reservationMapper.getDocCompletedList(map);
	}

	@Override
	public List<String> getReservationsByMember(Long mem_num) {
		return reservationMapper.getReservationsByMember(mem_num);
	}

	
}
