package juliomesquita.projectcore.persistence.common.factory.specification.predicatefactory.typeproperty;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import juliomesquita.projectcore.persistence.common.factory.specification.enums.ModelQueryFilterOperators;
import juliomesquita.projectcore.persistence.common.factory.specification.interfaces.SpringSpecificationPredicateFactory;
import juliomesquita.projectcore.persistence.common.factory.specification.predicatefactory.abstracts.AbstractSpecificationPredicateFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BigDecimalPropertySpecificationPredicateFactory
    extends AbstractSpecificationPredicateFactory<BigDecimal>
    implements SpringSpecificationPredicateFactory<BigDecimal> {
    @Override
    public @NonNull Class<BigDecimal> getSupportedClass() {
        return BigDecimal.class;
    }

    @Override
    public @NonNull Predicate createSpecification(
            @NonNull Path<BigDecimal> propertyPath,
            @NonNull String value,
            @NonNull ModelQueryFilterOperators operator,
            @NonNull CriteriaBuilder criteriaBuilder
    ) {
        switch (operator) {
            case EQ -> {
                return criteriaBuilder.equal(propertyPath, toBigDecimal(value));
            }
            case NEQ -> {
                return criteriaBuilder.notEqual(propertyPath, value);
            }
            case GTE -> {
                return criteriaBuilder.greaterThanOrEqualTo(propertyPath, toBigDecimal(value));
            }
            case GT -> {
                return criteriaBuilder.greaterThan(propertyPath, toBigDecimal(value));
            }
            case LTE -> {
                return criteriaBuilder.lessThanOrEqualTo(propertyPath, toBigDecimal(value));
            }
            case LT -> {
                return criteriaBuilder.lessThan(propertyPath, toBigDecimal(value));
            }
            case BETWEEN -> {
                List<BigDecimal> multipleBigDecimal = toMultipleBigDecimal(value);
                if (multipleBigDecimal.size() < 2) {
                    throw new IllegalArgumentException(
                            "Could not use operator for BigDecimal property: two values required."
                    );
                }
                return criteriaBuilder.between(propertyPath, multipleBigDecimal.get(0), multipleBigDecimal.get(1));
            }
            case IN -> {
                return propertyPath.in(toMultipleBigDecimal(value));
            }
            case NOT_IN -> {
                return propertyPath.in(toMultipleBigDecimal(value)).not();
            }
            default -> throw new IllegalArgumentException(
                    "Cannot use operator for BigDecimal property " + operator + "."
            );
        }
    }

    @NonNull
    private BigDecimal toBigDecimal (@NonNull String value) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException(
                    "Could not convert value to BigDecimal: " + value + "."
            );
        }
        return new BigDecimal(value);
    }
    @NonNull
    private List<BigDecimal> toMultipleBigDecimal (@NonNull String value) {
        List<String> valueSplit = this.splitValue(value);
        return valueSplit.stream()
                .map(v -> toBigDecimal(value))
                .toList();
    }


}
