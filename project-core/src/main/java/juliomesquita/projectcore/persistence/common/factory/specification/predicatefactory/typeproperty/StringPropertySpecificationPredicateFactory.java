package juliomesquita.projectcore.persistence.common.factory.specification.predicatefactory.typeproperty;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import juliomesquita.projectcore.persistence.common.factory.specification.enums.ModelQueryFilterOperators;
import juliomesquita.projectcore.persistence.common.factory.specification.interfaces.SpringSpecificationPredicateFactory;
import juliomesquita.projectcore.persistence.common.factory.specification.predicatefactory.abstracts.AbstractSpecificationPredicateFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StringPropertySpecificationPredicateFactory
    extends AbstractSpecificationPredicateFactory<String>
    implements SpringSpecificationPredicateFactory<String> {
    @Override
    public @NonNull Class<String> getSupportedClass() {
        return String.class;
    }

    @Override
    public @NonNull Predicate createSpecification(
            @NonNull Path<String> propertyPath,
            @NonNull String value,
            @NonNull ModelQueryFilterOperators operator,
            @NonNull CriteriaBuilder criteriaBuilder
    ) {
        switch (operator) {
            case EQ -> {
                return criteriaBuilder.equal(propertyPath, value);
            }
            case IN -> {
                return propertyPath.in(splitValue(value));
            }
            case NOT_IN -> {
                return propertyPath.in(splitValue(value)).not();
            }
            case LIKE -> {
                return criteriaBuilder.like(propertyPath, "%" + value + "%");
            }
            case NOT_LIKE -> {
                return criteriaBuilder.like(propertyPath, "%" + value + "%").not();
            }
            default -> throw new IllegalArgumentException(
                    "Cannot use operator for string property" + operator + "."
            );
        }
    }
}
