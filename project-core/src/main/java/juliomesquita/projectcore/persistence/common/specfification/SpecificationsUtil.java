package juliomesquita.projectcore.persistence.common.specfification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SpecificationsUtil {

    public static <T> Specification<T> joinSpecsWithAnd(
            Specification<T>... specifications
    ) {
        return joinSpecs(true, specifications);
    }

    public static <T> Specification<T> joinSpecsWithOr(
            Specification<T>... specifications
    ) {
        return joinSpecs(false, specifications);
    }

    private static <T> Specification<T> joinSpecs(
            boolean isAnd,
            Specification<T>[] specifications
    ) {
        Specification<T> spec = Specification.where(null);

        if(Objects.isNull(specifications) || specifications.length < 1) {
            return spec;
        }

        for (Specification<T> specification : specifications) {
            if(Objects.isNull(specification)){
                continue;
            }
            if(isAnd){
                spec = spec.and(specification);
            } else {
                spec = spec.or(specification);
            }
        }
        return spec;
    }
}
