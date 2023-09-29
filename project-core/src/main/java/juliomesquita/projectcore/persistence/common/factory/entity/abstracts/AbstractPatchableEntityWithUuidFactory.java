package juliomesquita.projectcore.persistence.common.factory.entity.abstracts;

import juliomesquita.projectcore.persistence.common.BaseEntity;
import juliomesquita.projectcore.persistence.common.factory.entity.interfaces.PatchableEntityFactory;

import java.util.UUID;

public abstract class AbstractPatchableEntityWithUuidFactory<
    E extends BaseEntity<UUID>,
    C, U, P>
  extends AbstractPatchableEntityFactory<E, UUID, C, U, P>
  implements PatchableEntityFactory<E, UUID, C, U, P> {
}
