package juliomesquita.projectcore.persistence.common.repository;

import lombok.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ReadOnlyRepository<T, I> extends Repository<T,I> {
    Optional<T> findById(@NonNull I id);
    List<T> findAll();
}
