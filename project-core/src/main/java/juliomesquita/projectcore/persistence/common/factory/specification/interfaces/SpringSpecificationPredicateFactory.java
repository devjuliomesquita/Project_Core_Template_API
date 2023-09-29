package juliomesquita.projectcore.persistence.common.factory.specification.interfaces;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import juliomesquita.projectcore.persistence.common.factory.specification.enums.ModelQueryFilterOperators;
import lombok.NonNull;

public interface SpringSpecificationPredicateFactory<T> {

    @NonNull
    Class<T> getSupportedClass();

    @NonNull
    Predicate createSpecification(
            @NonNull Path<T> propertyPath,
            @NonNull String value,
            @NonNull ModelQueryFilterOperators operator,
            @NonNull CriteriaBuilder criteriaBuilder
            );
}
