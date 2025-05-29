package ksy.medichat.hospital.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ksy.medichat.filter.Filter;
import ksy.medichat.hospital.domain.Hospital;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static ksy.medichat.hospital.domain.QHospital.hospital;

@Repository
@RequiredArgsConstructor
public class HospitalRepositoryImpl implements HospitalRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Hospital> findByFilter(Pageable pageable, Filter filter) {
        BooleanBuilder builder = new BooleanBuilder();
        String keyword = filter.getKeyword();

        if(!StringUtils.isEmpty(filter.getKeyword())){
            builder.and(hospital.hosName.eq(keyword));
            builder.or(hospital.hosAddr.eq(keyword));
            builder.or(hospital.hosInfo.eq(keyword));
            /*if(!keyword.equals("가정의학과")&&!keyword.equals("내과")&&!keyword.equals("마취통증")&&!keyword.equals("비뇨기과")&&!keyword.equals("산부인과")&&!keyword.equals("성형외과")&&!keyword.equals("소아과")&&!keyword.equals("신경과")&&!keyword.equals("신경외과")&&!keyword.equals("안과")&&!keyword.equals("영상의학과")&&!keyword.equals("외과")&&!keyword.equals("응급의학과")&&!keyword.equals("이비인후과")&&!keyword.equals("재활의학과")&&!keyword.equals("정신건강의학과")&&!keyword.equals("정형외과")&&!keyword.equals("치과")&&!keyword.equals("피부과")&&!keyword.equals("한의원")) {
                kSet = diseaseService.selectDisListBykeyword(keyword);
                if(kSet!=null) {
                    for(String key: kSet) {
                        sub_sql += "OR hos_name LIKE '%' || '" + key + "' || '%' ";
                    }
                }
            }*/
        }

        return queryFactory
                .selectFrom(hospital)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
