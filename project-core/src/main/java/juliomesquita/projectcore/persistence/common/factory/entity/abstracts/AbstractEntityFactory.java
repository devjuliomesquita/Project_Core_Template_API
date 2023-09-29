package juliomesquita.projectcore.persistence.common.factory.entity.abstracts;

import juliomesquita.projectcore.persistence.common.BaseEntity;
import juliomesquita.projectcore.persistence.common.factory.entity.interfaces.EntityFactory;
import juliomesquita.projectcore.service.common.datetime.format.DateTimeFormatterProvider;
import juliomesquita.projectcore.service.common.json.JsonService;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.openapitools.jackson.nullable.JsonNullable;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractEntityFactory<E extends BaseEntity<I>, I>
    implements EntityFactory<E, I> {

  private Class<E> resolvedGenericClass;

  protected Optional<LocalDate> parseToLocalDate(
      JsonNullable<String> value,
      @NonNull String pattern
  ) {
  return DateTimeFormatterProvider.parseToLocalDate(value, pattern);
  }
  protected Optional<LocalDate> parseToLocalDate(
      String value,
      @NonNull String pattern
  ) {
    return DateTimeFormatterProvider.parseToLocalDate(value, pattern);
  }

  @NonNull
  public E creteEntityReference(
      @NonNull JsonNullable<String> id
  ) {
    String idValue = JsonService.getJsonNullableValue(id)
        .orElseThrow(() -> new IllegalStateException("Cannot generate id from blank value")
        );
    return this.creteEntityReference(idValue);
  }

  @NonNull
  public E creteEntityReference(
      @NonNull String id
  ) {
    if (StringUtils.isBlank(id)) {
      throw new IllegalStateException("Cannot generate id from blank value");
    }
    I idValue = this.createIdForReference(id);
    return this.creteEntityReference(idValue);
  }

  @NonNull
  public E creteEntityReference(
      @NonNull I id
  ) {
    E entity = this.callEntityNoArgsConstructor();
    entity.setId(id);
    return entity;
  }

  @NonNull
  protected abstract I createIdForReference(@NonNull String id);

  private E callEntityNoArgsConstructor() {
    try {
      return this.getResolvedGenericClass().getConstructor().newInstance();
    } catch (Exception e) {
      throw new IllegalStateException("Could not call no args constructor for entity.");
    }
  }

  private Class<E> getResolvedGenericClass() {
    if (Objects.isNull(this.resolvedGenericClass)) {
      this.resolvedGenericClass = (Class<E>) (
          (ParameterizedType) getClass().getGenericSuperclass()
      ).getActualTypeArguments()[0];
    }
    return  this.resolvedGenericClass;
  }




}
