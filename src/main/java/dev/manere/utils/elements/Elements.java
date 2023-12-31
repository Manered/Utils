package dev.manere.utils.elements;

import dev.manere.utils.consumers.PairConsumer;
import dev.manere.utils.elements.impl.ElementsImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * An interface representing a collection of elements with various operations.
 *
 * @param <E> the type of elements stored in this collection
 */
@SuppressWarnings("unchecked") // I don't want arrays.
public interface Elements<E> extends Iterable<E> {
    /**
     * Creates an empty Elements instance.
     *
     * @param <E> the type of elements
     * @return an empty Elements instance
     */
    static <E> @NotNull ElementsImpl<E> of() {
        return new ElementsImpl<>();
    }

    /**
     * Creates an empty Elements instance with a specified type.
     *
     * @param <E>  the type of elements
     * @param type the class representing the type of elements
     * @return an empty Elements instance with the specified type
     */
    @SuppressWarnings("Convert2Diamond") // Honestly this method is pointless
    static <E> @NotNull ElementsImpl<E> of(@NotNull Class<E> type) {
        return new ElementsImpl<E>();
    }

    /**
     * Creates an Elements instance with elements from a Collection.
     *
     * @param <E>      the type of elements
     * @param elements a Collection of elements
     * @return an Elements instance with elements from the Collection
     */
    static <E> @NotNull ElementsImpl<E> of(@Nullable Collection<E> elements) {
        ElementsImpl<E> elementsImpl = new ElementsImpl<>();
        elementsImpl.elements(elements);

        return elementsImpl;
    }

    /**
     * Creates an Elements instance with elements from an array.
     *
     * @param <E>      the type of elements
     * @param elements an array of elements
     * @return an Elements instance with elements from the array
     */
    @SafeVarargs
    static <E> @NotNull ElementsImpl<E> of(@NotNull E... elements) {
        ElementsImpl<E> elementsImpl = new ElementsImpl<>();
        elementsImpl.elements(elements);

        return elementsImpl;
    }

    /**
     * Sets the elements of this collection from a Collection.
     *
     * @param elements a Collection of elements
     */
    void elements(@NotNull Collection<E> elements);

    /**
     * Sets the elements of this collection from an array.
     *
     * @param elements an array of elements
     */
    void elements(@NotNull E... elements);

    /**
     * Adds a single element to this collection.
     *
     * @param element the element to be added
     */
    void element(@Nullable E element);

    /**
     * Inserts an element at the specified position.
     *
     * @param at      the position at which to insert the element
     * @param element the element to insert
     */
    void element(int at, @Nullable E element);

    /**
     * Retrieves the element at the specified position.
     *
     * @param at the position of the element to retrieve
     * @return the element at the specified position
     */
    @Nullable E element(int at);

    /**
     * Returns the position of the specified element.
     *
     * @param element the element to find
     * @return the position of the element, or -1 if not found
     */
    int at(@NotNull E element);

    /**
     * Removes a single occurrence of the specified element from this collection.
     *
     * @param element the element to remove
     */
    void del(@NotNull E element);

    /**
     * Removes the element at the specified position.
     *
     * @param at the position of the element to remove
     */
    void del(int at);

    /**
     * Removes the specified element at the specified position.
     *
     * @param at      the position of the element to remove
     * @param element the element to remove
     */
    void del(int at, @NotNull E element);

    /**
     * Removes all occurrences of elements from a Collection.
     *
     * @param elements a Collection of elements to remove
     */
    void delAll(@NotNull Collection<E> elements);

    /**
     * Removes all occurrences of elements from an array.
     *
     * @param elements an array of elements to remove
     */
    void delAll(@NotNull E... elements);

    /**
     * Checks if the collection contains the specified element.
     *
     * @param element the element to check for
     * @return true if the collection contains the element, false otherwise
     */
    boolean has(@NotNull E element);

    /**
     * Performs the given action for each element in the collection.
     *
     * @param action the action to be performed on each element
     */
    @Override
    void forEach(Consumer<? super E> action);

    /**
     * Performs the given action for each element in the collection.
     *
     * @param elementConsumer the action to be performed on each element
     */
    void forEach(@NotNull PairConsumer<Integer, E> elementConsumer);

    /**
     * {@inheritDoc}
     */
    @Override
    Iterator<E> iterator();

    /**
     * Retrieves all elements in the collection.
     *
     * @return a Collection of all elements
     */
    @NotNull Collection<E> elements();

    /**
     * Retrieves all elements in the collection as an array.
     *
     * @return an array of all elements
     */
    @NotNull E[] elementsArray();

    /**
     * Clears the elements.
     */
    void clear();

    /**
     * Retrieves the number of elements in the collection.
     *
     * @return the number of elements in the collection
     */
    int size();
}
