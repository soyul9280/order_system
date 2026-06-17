package project.orderminisystem.domain.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import project.orderminisystem.domain.product.entity.Product;
import project.orderminisystem.domain.product.entity.QProduct;
import project.orderminisystem.global.common.Direction;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductCustomRepository{
    private final JPAQueryFactory queryFactory;
    private final QProduct product = QProduct.product;

    @Override
    public List<Product> searchProducts(String keyword, String orderBy, Direction direction, String cursor,
                                        LocalDateTime after, int limit) {

        BooleanBuilder condition = new BooleanBuilder();
        // 키워드 검색 -> 상품명, 설명으로 검색
        if (StringUtils.hasText(keyword)) {
            condition.andAnyOf(
                    product.itemName.containsIgnoreCase(keyword),
                    product.description.containsIgnoreCase(keyword));
        }
        // 논리 삭제 제외
        condition.and(product.isDeleted.eq(false));

        // 커서 페이지네이션 조건 추가하기
        if (cursor != null && after != null) {
            condition.and(getCursorCondition(orderBy, cursor, after, isAsc(direction)));
        }

        // 정렬 조건 추가하기
        List<OrderSpecifier<?>> orderSpecifiers = buildOrderSpecifiers(orderBy, isAsc(direction));

        return queryFactory
                .selectFrom(product)
                .where(condition)
                .orderBy(orderSpecifiers.toArray(OrderSpecifier[]::new))
                .limit(limit)
                .fetch();
    }

    private boolean isAsc(Direction direction) {
        return "ASC".equalsIgnoreCase(String.valueOf(direction));
    }

    private BooleanBuilder getCursorCondition(
            String sortBy, String cursor, LocalDateTime after, boolean isAsc) {
        BooleanBuilder builder = new BooleanBuilder();

        // 정렬 필드 -> 상품명, 기본=생성일
        switch (sortBy) {
            case "itemName" -> {
                builder.and(
                        isAsc
                                ? product.itemName.gt(cursor).or(product.itemName.eq(cursor).and(product.createdAt.gt(after)))
                                : product.itemName.lt(cursor).or(product.itemName.eq(cursor).and(product.createdAt.lt(after))));
            }
            default -> builder.and(isAsc ? product.createdAt.gt(after) : product.createdAt.lt(after));
        }
        return builder;
    }

    // 정렬 기준 필드 + createdAt을 함께 적용한 Order By 조건을 생성함
    private List<OrderSpecifier<?>> buildOrderSpecifiers(String sortBy, boolean isAsc) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        switch (sortBy) {
            case "itemName" -> orderSpecifiers.add(isAsc ? product.itemName.asc() : product.itemName.desc());
            default -> orderSpecifiers.add(isAsc ? product.createdAt.asc() : product.createdAt.desc());
        }

        orderSpecifiers.add(isAsc ? product.createdAt.asc() : product.createdAt.desc());
        return orderSpecifiers;
    }
}
