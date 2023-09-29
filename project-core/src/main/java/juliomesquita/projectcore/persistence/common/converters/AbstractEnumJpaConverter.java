package juliomesquita.projectcore.persistence.common.converters;


import jakarta.persistence.AttributeConverter;
import juliomesquita.projectcore.exception.PersistenceConversionProblem;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;


import java.lang.reflect.ParameterizedType;
import java.util.Objects;
import java.util.Optional;



@Slf4j
public abstract class AbstractEnumJpaConverter<
        E extends Enum<E> & HasDbValue<V>,
        V
        > implements AttributeConverter<E, V> {

    private final Class<E> enumClass;

    protected AbstractEnumJpaConverter(Class<E> enumClass) {
        this.enumClass = (Class<E>) (
                (ParameterizedType) getClass().getGenericSuperclass()
                ).getActualTypeArguments()[0];
    }

    protected abstract Optional<E> getEnumMemberWithDbValue(V dbValue);

    @Override
    public V convertToDatabaseColumn(E e) {
        return Objects.nonNull(e)
                ? e.getDbValue()
                : null;
    }

    @Override
    public E convertToEntityAttribute(V dbValue) {
        Optional<E> enumMemberOpt = getEnumMemberWithDbValue(dbValue);
        if (enumMemberOpt.isPresent()) {
            return enumMemberOpt.get();
        } else {
            throw new PersistenceConversionProblem();
        }
    }
}
