package juliomesquita.projectcore.persistence.common.factory.entity.interfaces;

import juliomesquita.projectcore.persistence.common.BaseEntity;
import lombok.NonNull;

public interface CrudEntityFactory<
    E extends BaseEntity<I>,
    I,
    C, U
    > extends EntityFactory<E, I> {

  E fromCreateRequest(@NonNull C creteRequest);

  E fromUpdateRequest(@NonNull U updateRequest, @NonNull I id);
}
