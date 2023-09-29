package juliomesquita.projectcore.persistence.common;

public interface BaseEntity<I> {
    I getId();
    void setId(I id);
}
