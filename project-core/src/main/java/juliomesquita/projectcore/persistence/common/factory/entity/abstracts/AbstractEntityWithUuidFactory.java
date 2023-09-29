package juliomesquita.projectcore.persistence.common.factory.entity.abstracts;

import juliomesquita.projectcore.persistence.common.BaseEntity;
import lombok.NonNull;

import java.util.UUID;


public abstract class AbstractEntityWithUuidFactory<E extends BaseEntity<UUID>>
    extends AbstractEntityFactory<E, UUID> {

  @Override
  protected @NonNull UUID createIdForReference(@NonNull String id) {
    return UUID.fromString(id);
  }
}
