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

import java.util.List;

@Component
@RequiredArgsConstructor
public class IntegerPropertySpecificationPredicateFactory
    extends AbstractSpecificationPredicateFactory<Integer>
    implements SpringSpecificationPredicateFactory<Integer> {
    @Override
    public @NonNull Class<Integer> getSupportedClass() {
        return Integer.class;
    }

    @Override
    public @NonNull Predicate createSpecification(
            @NonNull Path<Integer> propertyPath,
            @NonNull String value,
            @NonNull ModelQueryFilterOperators operator,
            @NonNull CriteriaBuilder criteriaBuilder
    ) {
        switch (operator) {
            case EQ -> {
                return criteriaBuilder.equal(propertyPath, toInteger(value));
            }
            case NEQ -> {
                return criteriaBuilder.notEqual(propertyPath, toInteger(value));
            }
            case GTE -> {
                return criteriaBuilder.greaterThanOrEqualTo(propertyPath, toInteger(value));
            }
            case GT -> {
                return criteriaBuilder.greaterThan(propertyPath, toInteger(value));
            }
            case LTE -> {
                return criteriaBuilder.lessThanOrEqualTo(propertyPath, toInteger(value));
            }
            case LT -> {
                return criteriaBuilder.lessThan(propertyPath, toInteger(value));
            }
            case BETWEEN -> {
                List<Integer> multipleInteger = toMultipleInteger(value);
                if (multipleInteger.size() < 2) {
                    throw new IllegalArgumentException(
                            "Cannot use operator for Integer property: two values required."
                    );
                }
                return criteriaBuilder.between(
                        propertyPath, multipleInteger.get(0), multipleInteger.get(1)
                );
            }
            case IN -> {
                return propertyPath.in(toMultipleInteger(value));
            }
            case NOT_IN -> {
                return propertyPath.in(toMultipleInteger(value)).not();
            }
            default -> throw new IllegalArgumentException(
                    "Cannot use operator for Integer property: " + operator + "."
            );
        }
    }

    @NonNull
    private Integer toInteger(@NonNull String value) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException(
                    "Could not convert value to Integer: " + value + "."
            );
        }
        return Integer.parseInt(value);
    }

    @NonNull
    private List<Integer> toMultipleInteger(@NonNull String value) {
        List<String> valueSplit = this.splitValue(value);
        return valueSplit.stream()
                .map(i -> toInteger(value))
                .toList();
    }
}
