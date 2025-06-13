package ksy.medichat.hospital.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ksy.medichat.filter.Search;
import ksy.medichat.hospital.domain.QHospital;
import ksy.medichat.hospital.dto.HospitalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class HospitalRepositoryImpl implements HospitalRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<HospitalDTO> findByFilter(Pageable pageable, Search search) {
        QHospital h = QHospital.hospital;

        Expression<Double> aroundExpr = Expressions.numberTemplate(
                Double.class,
                "6378137 * acos(" +
                        "cos(radians({0})) * cos(radians({1})) * cos(radians({2}) - radians({3})) + " +
                        "sin(radians({0})) * sin(radians({1}))" +
                        ")",
                search.getLocation().getUserLat(), h.lat, h.lng, search.getLocation().getUserLng()
        );

        double latitude = 37.5; // 서울 기준
        // 위도는 1도 ≈ 111,000m
        Double latOffset = search.getMaxDistance() / 111000.0;
        // 경도는 위도에 따라 다름 (cos 위도 적용)
        Double lonOffset = search.getMaxDistance() / (111000.0 * Math.cos(Math.toRadians(latitude)));

        BooleanBuilder whereBuilder = new BooleanBuilder();
        whereBuilder.and(h.lat.between(search.getLocation().getUserLat() - latOffset, search.getLocation().getUserLat() + latOffset));
        whereBuilder.and(h.lng.between(search.getLocation().getUserLng() - lonOffset, search.getLocation().getUserLng() + lonOffset));
        whereBuilder.and(applyKeywordFilter(search.getKeyword(), h));
        whereBuilder.and(applyCommonFilter(search, h));
        whereBuilder.and(applyTimeFilter(search));

        return queryFactory
                .select(h)
                .from(h)
                // 추후 doctor_detail, review 테이블 생성 후 아래 LEFT JOIN 재사용 가능
                // .leftJoin(...).fetchJoin()
                .where(whereBuilder)
                .orderBy(resolveSort(search.getSortType(), h, aroundExpr))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().map(HospitalDTO::toDTO).collect(Collectors.toList());
    }
    private BooleanExpression applyKeywordFilter(String keyword, QHospital h) {
        if (keyword == null || keyword.trim().isEmpty()) return null;
        return h.address.containsIgnoreCase(keyword)
                .or(h.name.containsIgnoreCase(keyword))
                .or(h.description.containsIgnoreCase(keyword));
    }
    private BooleanBuilder applyCommonFilter(Search search, QHospital h) {
        BooleanBuilder builder = new BooleanBuilder();
        String filters = search.getCommonFilter();

        if (filters == null || filters.isEmpty()) return builder;

        if (filters.contains("WEEKEND")) {
            builder.and(h.weekendAt.eq("Y"));
        }

        // 추후 doctor_detail 테이블 생성 후 활성화
        /*
        if (filters.contains("NONFACE")) {
            builder.and(Expressions.booleanTemplate("doc_cnt > 0"));
        }
        */

        return builder;
    }
    private BooleanBuilder applyTimeFilter(Search search) {
        BooleanBuilder builder = new BooleanBuilder();
        String filters = search.getCommonFilter();
        int day = search.getDate().getDay();

        if (filters == null || filters.isEmpty()) return builder;

        String timeField = "time" + day + "c";

        if (filters.contains("ING")) {
            builder.and(Expressions.booleanTemplate("{0} IS NOT NULL AND {0} >= {1}", timeField, search.getDate().getTime()));
        }

        if (filters.contains("NIGHTTIME")) {
            String nightTime = (day <= 5) ? "1800" : "1300";
            builder.and(Expressions.booleanTemplate("{0} IS NOT NULL AND {0} > {1}", timeField, nightTime));
        }

        return builder;
    }

    private OrderSpecifier<?> resolveSort(String sortType, QHospital h, Expression<Double> aroundExpr) {
        return switch (sortType) {
            /*case "REVIEW" -> new OrderSpecifier<>(Order.ASC, aroundExpr); // 필요하면 rev_cnt 추가 구현
            case "SCORE" -> h.hosScore.desc();
            case "HIT" -> h.hosHit.desc();*/
            default -> new OrderSpecifier<>(Order.ASC, aroundExpr);
        };
    }
}
