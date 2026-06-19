package project.orderminisystem.domain.order.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.orderminisystem.domain.order.entity.Order;
import project.orderminisystem.domain.order.entity.QOrder;
import project.orderminisystem.global.common.Direction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderCustomRepository {
    private final JPAQueryFactory queryFactory;
    private final QOrder order = QOrder.order;

    @Override
    public List<Order> searchOrders(String orderBy, Direction direction, String cursor,
                                    LocalDateTime after, int limit) {

        BooleanBuilder condition = new BooleanBuilder();

        // 커서 페이지네이션 조건 추가하기
        if (after != null) {
            condition.and(getCursorCondition(after, isAsc(direction)));
        }

        // 정렬 조건 추가하기
        List<OrderSpecifier<?>> orderSpecifiers = buildOrderSpecifiers(isAsc(direction));

        return queryFactory
                .selectFrom(order)
                .where(condition)
                .orderBy(orderSpecifiers.toArray(OrderSpecifier[]::new))
                .limit(limit)
                .fetch();
    }

    private boolean isAsc(Direction direction) {
        return "ASC".equalsIgnoreCase(String.valueOf(direction));
    }

    private BooleanBuilder getCursorCondition(LocalDateTime after, boolean isAsc) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(isAsc ? order.createdAt.gt(after) : order.createdAt.lt(after));
        return builder;
    }

    // 정렬 기준 필드 + createdAt을 함께 적용한 Order By 조건을 생성함
    private List<OrderSpecifier<?>> buildOrderSpecifiers(boolean isAsc) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        orderSpecifiers.add(isAsc ? order.createdAt.asc() : order.createdAt.desc());
        return orderSpecifiers;
    }
}
