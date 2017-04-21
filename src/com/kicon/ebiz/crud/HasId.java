package com.kicon.ebiz.crud;

/**
 * Interface that specifies the id of an entity.
 */
public interface HasId<T> {

    /**
     * Retrieves id.
     * @return The id.
     */
    public T getId();

    /**
     * Sets id.
     * @param id The new id.
     */
    public void setId(T id);
}
