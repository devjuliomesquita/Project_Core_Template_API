package juliomesquita.projectcore.persistence.common.factory.entity.abstracts;

import juliomesquita.projectcore.persistence.common.BaseEntity;
import juliomesquita.projectcore.persistence.common.factory.entity.interfaces.PatchableEntityFactory;

public abstract class AbstractPatchableEntityFactory<
    E extends BaseEntity<I>,
    I,
    C, U, P>
  extends AbstractCrudEntityFactory<E, I, C, U>
  implements PatchableEntityFactory<E, I, C, U, P> {
}
