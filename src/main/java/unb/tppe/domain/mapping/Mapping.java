package unb.tppe.domain.mapping;

public interface Mapping<E, S> {
    public E toDomain(S schema);
    public S toSchema(E entity);
}
