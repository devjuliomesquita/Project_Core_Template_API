package juliomesquita.projectcore.persistence.common.factory.entity.abstracts;

import juliomesquita.projectcore.persistence.common.BaseEntity;
import juliomesquita.projectcore.persistence.common.factory.entity.interfaces.CrudEntityFactory;

import java.util.UUID;

public abstract class AbstractCrudEntityWithUuidFactory<E extends BaseEntity<UUID>, C, U>
  extends AbstractEntityWithUuidFactory<E>
  implements CrudEntityFactory<E, UUID, C, U> {
}
