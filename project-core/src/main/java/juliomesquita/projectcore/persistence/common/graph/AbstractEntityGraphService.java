package juliomesquita.projectcore.persistence.common.graph;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import juliomesquita.projectcore.persistence.common.BaseEntity;
import lombok.NonNull;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class AbstractEntityGraphService<E extends BaseEntity<I>, I> {
    private final EntityManager entityManager;
    private Class<E> resolvedGenericsClass;

    protected AbstractEntityGraphService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private Class<E> getResolvedGenericsClass() {
        if (Objects.isNull(this.resolvedGenericsClass)) {
            this.resolvedGenericsClass = (Class<E>) (
                    (ParameterizedType) getClass().getGenericSuperclass()
                    ).getActualTypeArguments()[0];
        }
        return this.resolvedGenericsClass;
    }

    protected Optional<E> loadByGraph(
            @NonNull Consumer<EntityGraph<E>> entiyGraphCostumizer,
            @NonNull I id
            ) {
        EntityGraph<E> entityGraph = entityManager.createEntityGraph(getResolvedGenericsClass());
        entiyGraphCostumizer.accept(entityGraph);

        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.fetchgraph", entityGraph);

        E entity = entityManager.find(getResolvedGenericsClass(), id, properties);
        return Optional.ofNullable(entity);
    }
}
