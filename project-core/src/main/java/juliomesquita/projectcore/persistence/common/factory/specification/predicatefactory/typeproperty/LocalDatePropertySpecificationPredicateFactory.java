package juliomesquita.projectcore.persistence.common.factory.specification.predicatefactory.typeproperty;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import juliomesquita.projectcore.persistence.common.factory.specification.enums.ModelQueryFilterOperators;
import juliomesquita.projectcore.persistence.common.factory.specification.interfaces.SpringSpecificationPredicateFactory;
import juliomesquita.projectcore.persistence.common.factory.specification.predicatefactory.abstracts.AbstractSpecificationPredicateFactory;
import juliomesquita.projectcore.service.common.datetime.format.DateTimeFormatterProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LocalDatePropertySpecificationPredicateFactory
    extends AbstractSpecificationPredicateFactory<LocalDate>
    implements SpringSpecificationPredicateFactory<LocalDate> {

    @Value("${api.query.value.date.pattern:dd/MM/yyyy}")
    private String datePattern;
    @Override
    public @NonNull Class<LocalDate> getSupportedClass() {
        return LocalDate.class;
    }

    @Override
    public @NonNull Predicate createSpecification(
            @NonNull Path<LocalDate> propertyPath,
            @NonNull String value,
            @NonNull ModelQueryFilterOperators operator,
            @NonNull CriteriaBuilder criteriaBuilder
    ) {
        switch (operator) {
            case EQ -> {
                return criteriaBuilder.equal(propertyPath, toLocalDate(value));
            }
            case NEQ -> {
                return criteriaBuilder.notEqual(propertyPath, toLocalDate(value));
            }
            case GTE -> {
                return criteriaBuilder.greaterThanOrEqualTo(propertyPath, toLocalDate(value));
            }
            case GT -> {
                return criteriaBuilder.greaterThan(propertyPath, toLocalDate(value));
            }
            case LTE -> {
                return criteriaBuilder.lessThanOrEqualTo(propertyPath, toLocalDate(value));
            }
            case LT -> {
                return criteriaBuilder.lessThan(propertyPath, toLocalDate(value));
            }
            case BETWEEN -> {
                List<LocalDate> multipleLocalDate = toMultipleLocalDate(value);
                if (multipleLocalDate.size() < 2) {
                    throw new IllegalArgumentException(
                            "Could not use operator for LocalDate property: two values required."
                    );
                }
                return criteriaBuilder.between(
                        propertyPath, multipleLocalDate.get(0), multipleLocalDate.get(1)
                );
            }
            case IN -> {
                return propertyPath.in(toMultipleLocalDate(value));
            }
            case NOT_IN -> {
                return propertyPath.in(toMultipleLocalDate(value)).not();
            }
            default -> throw new IllegalArgumentException(
                    "Cannot use operator for LacalDate property: " + operator + "."
            );
        }
    }

    @NonNull
    private LocalDate toLocalDate(@NonNull String value) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException(
                    "Could not convert value to LocalDate: " + value + "."
            );
        }
        return LocalDate.parse(value, getDateTimeFormatter());
    }

    private DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatterProvider.getInstance(datePattern);
    }

    @NonNull
    private List<LocalDate> toMultipleLocalDate(@NonNull String value) {
        List<String> valueSplit = this.splitValue(value);
        return valueSplit.stream()
                .map(l -> toLocalDate(value))
                .toList();
    }
}
