package juliomesquita.projectcore.persistence.common.factory.entity.abstracts;

import juliomesquita.projectcore.persistence.common.BaseEntity;
import juliomesquita.projectcore.persistence.common.factory.entity.interfaces.CrudEntityFactory;

public abstract class AbstractCrudEntityFactory<E extends BaseEntity<I>, I, C, U>
  extends AbstractEntityFactory<E, I>
  implements CrudEntityFactory<E, I, C, U> {
}
