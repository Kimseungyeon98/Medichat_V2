package ksy.medichat.hospital.init;

import ksy.medichat.hospital.domain.HospitalDepartment;
import ksy.medichat.hospital.repository.HospitalDepartmentRepository;
import ksy.medichat.hospital.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class HospitalDepartmentInit implements ApplicationRunner {

    @Autowired
    private HospitalDepartmentRepository hospitalDepartmentRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(hospitalDepartmentRepository.count() == 0) {
            List<HospitalDepartment> list = new ArrayList<>();
            list.add(new HospitalDepartment("가정의학과", "건강증진, 예방, 만성질환 등", "home"));
            list.add(new HospitalDepartment("내과","감기, 소화기, 호흡기 등", "physician"));
            list.add(new HospitalDepartment("마취통증학과","급성통증, 만성통증 등", "syringe"));
            list.add(new HospitalDepartment("비뇨기과","소변 시 통증, 남성 질환 등", "urology"));
            list.add(new HospitalDepartment("산부인과","피임상담, 여성질환 등", "gynecology"));
            list.add(new HospitalDepartment("성형외과","피부질환, 화상, 상처 등", "beauty"));
            list.add(new HospitalDepartment("소아과","소아소화기, 소아호흡기, 알레르기 등", "child"));
            list.add(new HospitalDepartment("신경과","두통, 어지럼증, 뇌졸중 등", "brain"));
            list.add(new HospitalDepartment("신경외과","요통, 디스크, 신경계 질환 등", "headache"));
            list.add(new HospitalDepartment("안과","눈 피로, 결막염, 다래끼 등", "eye"));
            list.add(new HospitalDepartment("영상의학과","방사선 촬영, MRI, CT", "x-rays"));
            list.add(new HospitalDepartment("외과","갑상선, 유방, 하지정맥 등", "bone"));
            list.add(new HospitalDepartment("응급의학과","심한 탈수, 급성처치 등", "ambulance"));
            list.add(new HospitalDepartment("이비인후과","비염, 이명, 편도염 등", "ear"));
            list.add(new HospitalDepartment("재활의학과","신체회복, 물리치료, 만성통증 등", "medical"));
            list.add(new HospitalDepartment("정신건강의학과","수면장애, 스트레스, 중독 등", "mental"));
            list.add(new HospitalDepartment("정형외과","관절염, 골절, 척추 측만증 등", "surgical"));
            list.add(new HospitalDepartment("치과","치아질환, 잇몸질환, 턱 관절 등", "tooth"));
            list.add(new HospitalDepartment("피부과","두드러기, 가려움증, 탈모 등", "skin"));
            list.add(new HospitalDepartment("한의원","한방 진료, 다이어트, 경옥고 등", "treatment"));
            hospitalDepartmentRepository.saveAll(list);
        }
    }
}
