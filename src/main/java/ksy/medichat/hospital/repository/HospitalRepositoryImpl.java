package ksy.medichat.hospital.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ksy.medichat.filter.Filter;
import ksy.medichat.hospital.domain.Hospital;
import ksy.medichat.hospital.domain.QHospital;
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
        QHospital h = QHospital.hospital;
        BooleanBuilder builder = new BooleanBuilder();

        // 위도/경도 조건 (약 4.4km 이내)
        if (filter.getAround() != null && filter.getUser_lat() != null && filter.getUser_lon() != null) {
            double offset = filter.getAround() / 100000.0;
            builder.and(h.hosLat.between(String.valueOf(filter.getUser_lat() - offset), String.valueOf(filter.getUser_lat() + offset)));
            builder.and(h.hosLon.between(String.valueOf(filter.getUser_lon() - offset), String.valueOf(filter.getUser_lon() + offset)));
        }

        // 키워드
        if (StringUtils.hasText(filter.getKeyword())) {
            builder.and(
                    h.hosAddr.contains(filter.getKeyword())
                            .or(h.hosName.contains(filter.getKeyword()))
                            .or(h.hosInfo.contains(filter.getKeyword()))
            );
        }

        // 공통 필터
        String commonFilter = filter.getCommonFilter();
        if (StringUtils.hasText(commonFilter)) {
            if (commonFilter.contains("NONFACE")) {
                // builder.and(Expressions.numberTemplate(Long.class, "0").gt(0)); // docCnt
            }
            if (commonFilter.contains("ING")) {
                String field = getHosTimeField(filter.getDay());
                if (field != null && StringUtils.hasText(filter.getTime())) {
                    builder.and(Expressions.stringPath(h, field).isNotNull()
                            .and(Expressions.stringPath(h, field).goe(filter.getTime())));
                }
            }
            if (commonFilter.contains("NIGHTTIME")) {
                String field = getHosTimeField(filter.getDay());
                String nightTime = (filter.getDay() == 6 || filter.getDay() == 7) ? "1300" : "1800";
                if (field != null) {
                    builder.and(Expressions.stringPath(h, field).isNotNull()
                            .and(Expressions.stringPath(h, field).gt(nightTime)));
                }
            }
            if (filter.getCommonFilter().contains("WEEKEND")) {
                builder.and(h.hosWeekendAt.eq("Y"));
            }
        }

        // 거리 계산식 <<- 문제 있음
        Expression<Double> aroundExpr = Expressions.numberTemplate(
                Double.class,
                "(6378137 * acos(cos(radians({0})) * cos(radians({1})) * cos(radians({2}) - radians({3})) + sin(radians({0})) * sin(radians({1}))))",
                filter.getUser_lat(), h.hosLat, h.hosLon, filter.getUser_lon()
        );

        // 정렬 조건
        OrderSpecifier<Double> sortOrder = new OrderSpecifier<>(
                Order.ASC, aroundExpr
        );
        if ("REVIEW".equals(filter.getSortType())) {
            // sortOrder = ... (revCnt 정렬 추가 시 구현)
        } else if ("SCORE".equals(filter.getSortType())) {
            //sortOrder = h.hosScore.desc();
        } else if ("HIT".equals(filter.getSortType())) {
            //sortOrder = h.hosHit.desc();
        }

        return queryFactory
                .selectFrom(hospital)
                .where(builder)
                .orderBy(sortOrder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
    private String getHosTimeField(int day) {
        return switch (day) {
            case 1 -> "hosTime1c";
            case 2 -> "hosTime2c";
            case 3 -> "hosTime3c";
            case 4 -> "hosTime4c";
            case 5 -> "hosTime5c";
            case 6 -> "hosTime6c";
            case 7 -> "hosTime7c";
            default -> null;
        };
    }
}
