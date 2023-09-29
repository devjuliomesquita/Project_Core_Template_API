package juliomesquita.projectcore.persistence.common.factory.entity.interfaces;

import juliomesquita.projectcore.persistence.common.BaseEntity;
import lombok.NonNull;

public interface PatchableEntityFactory<
    E extends BaseEntity<I>,
    I,
    C, U, P>
  extends CrudEntityFactory<E, I, C, U> {

  E fromPatchRequest(@NonNull P patchRequest, I id);
}
