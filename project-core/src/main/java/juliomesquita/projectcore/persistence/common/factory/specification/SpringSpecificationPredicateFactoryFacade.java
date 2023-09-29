package juliomesquita.projectcore.persistence.common.factory.specification;

import juliomesquita.projectcore.persistence.common.factory.specification.interfaces.SpringSpecificationPredicateFactory;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SpringSpecificationPredicateFactoryFacade {

    private final Map<Class<?>, SpringSpecificationPredicateFactory<?>> classToSpecFatoryMap;

    public SpringSpecificationPredicateFactoryFacade(
            List<SpringSpecificationPredicateFactory<?>> specFactories
    ) {
        this.classToSpecFatoryMap = specFactories.stream()
                .collect(Collectors.toMap(
                        SpringSpecificationPredicateFactory::getSupportedClass,
                        Function.identity(),
                        (v1, v2) -> {
                            throw new IllegalStateException(
                                    "Duplicated specification factory. Chack " + v1 + " and " + v2 + "."
                            );
                        }
                ));
    }

    public <T> Optional<SpringSpecificationPredicateFactory<T>> getFactory(@NonNull Class<T> clazz) {
        SpringSpecificationPredicateFactory<?> factory = this.classToSpecFatoryMap.get(clazz);
        if (Objects.isNull(factory)) {
            return Optional.empty();
        }
        SpringSpecificationPredicateFactory<T> castedFactory = (SpringSpecificationPredicateFactory<T>) factory;
        return Optional.of(castedFactory);
    }
}
