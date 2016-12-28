package io.github.azagniotov.generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A set of utility methods that create from provided raw non-generic
 * (or generic with unknown types) collections generic type-safe collection
 * shallow copies.
 * <p>
 * If a given raw collection contains an element of the wrong type, it will
 * result in a {@link ClassCastException}. Assuming a collection contains no
 * incorrectly typed elements prior to the time a generic type-safe collection
 * is generated, it is <i>guaranteed</i> that the generated collection cannot
 * contain an incorrectly typed element, hence eliminates heap pollution.
 * <p>
 * The generics mechanism in the language provides compile-time
 * (static) type checking, but it is possible to defeat this mechanism
 * with unchecked casts.  Usually this is not a problem, as the compiler
 * issues warnings on all such unchecked operations.  There are, however,
 * times when static type checking alone is not sufficient.  For example,
 * suppose a collection is passed to a third-party library and it is
 * imperative that the library code not corrupt the collection by
 * inserting an element of the wrong type.
 * <p>
 * Since {@code null} is considered to be a value of any reference
 * type, a {@link IllegalArgumentException} will be thrown during cast if a
 * given raw collection contains null elements.
 * <p>
 * To create from a provided collection a shallow copy of generic type-safe
 * collection takes {@code O(N)} running time, therefore please execute caution
 * before deciding to convert large collections.
 */
public final class TypeSafeConverter {

    private TypeSafeConverter() throws InstantiationException {
        throw new InstantiationException();
    }

    /**
     * Generates a type-safe {@link Collection} shallow copy from provided {@code collectionObject} {@link Object}.
     *
     * @param collectionObject the raw collection from which a new type-safe {@link Collection} shallow copy is to be made
     * @param valueClassType   the type of element that {@code collectionObject} is permitted to hold
     * @param collectionImpl   the implementation of the returned {@code collectionImpl}
     * @param <T>              the class of the objects in the {@code collectionImpl}
     * @param <C>              the class of the returned {@link Collection}
     * @return a new type-safe {@link Collection}, which is a shallow copy of the given raw non-generic {@code collectionObject}
     * @throws ClassCastException       if the class of an element found in the
     *                                  given raw collection prevents it from being added to generated type-safe collection.
     * @throws IllegalArgumentException if the given {@code collectionImpl} is {@code null}.
     */
    public static <T, C extends Collection<T>> C asCheckedCollection(final Object collectionObject, final Class<T> valueClassType, final C collectionImpl) {
        if (collectionObject == null) {
            throw new IllegalArgumentException("Collection object instance is null");
        }
        final Collection<?> rawCollection = (Collection) collectionObject;
        for (final Object rawCollectionValue : rawCollection) {
            collectionImpl.add(as(valueClassType, rawCollectionValue));
        }
        return collectionImpl;
    }

    /**
     * Generates a type-safe {@link Map} shallow copy from a given raw non-generic {@code mapObject} {@link Object}.
     *
     * @param mapObject      the map object from which a new type-safe {@link Map} shallow copy is to be made
     * @param valueClassType the type of element that {@code mapObject} is permitted to hold
     * @param mapImpl        the implementation of the returned {@code mapImpl}
     * @param <K>            the class of the key objects in the {@code mapImpl}
     * @param <V>            the class of the value objects in the {@code mapImpl}
     * @param <M>            the class of the returned {@link Map}
     * @return a new type-safe {@link Map}, which is a shallow copy of the given raw non-generic {@code mapObject}
     * @throws ClassCastException       if the class of an element found in the
     *                                  given raw collection prevents it from being added to generated type-safe collection.
     * @throws IllegalArgumentException if the given {@code mapImpl} is {@code null}.
     */
    public static <K, V, M extends Map<K, V>> M asCheckedMap(final Object mapObject, final Class<K> keyClassType, final Class<V> valueClassType, final M mapImpl) {
        if (mapObject == null) {
            throw new IllegalArgumentException("Map object instance is null");
        }
        final Map<?, ?> rawMap = (Map) mapObject;
        for (final Map.Entry<?, ?> rawEntry : rawMap.entrySet()) {
            mapImpl.put(as(keyClassType, rawEntry.getKey()), as(valueClassType, rawEntry.getValue()));
        }
        return mapImpl;
    }

    /**
     * Casts a given {@link Object} {@code instance} to a given {@link Class} type {@code targetClazz}
     *
     * @param targetClazz the type that the {@code instance} should be casted to
     * @param instance    an object instance to cast
     * @param <T>         the type class tha the {@code instance} should be casted to
     * @return a typed object instance
     * @throws ClassCastException       if the given {@code instance} cannot be cast to {@code targetClazz}.
     * @throws IllegalArgumentException if the given {@code instance} is {@code null}.
     */
    public static <T> T as(final Class<T> targetClazz, final Object instance) {
        checkCast(targetClazz, instance);
        return targetClazz.cast(instance);
    }

    static <T> void checkCast(final Class<T> targetClazz, final Object instance) {
        if (instance == null) {
            throw new IllegalArgumentException("Object instance is null");
        }
        if (!targetClazz.isInstance(instance)) {
            throw new ClassCastException("Expected: " + targetClazz.getCanonicalName() + ", instead got: " + instance.getClass().getCanonicalName() + " for instance: " + instance);
        }
    }

    /**
     * @see #asCheckedCollection(Object, Class, Collection)
     */
    public static <T> Iterable<T> asCheckedIterable(final Object iterableObject, final Class<T> valueClassType) {
        return asCheckedCollection(iterableObject, valueClassType, new ArrayList<>());
    }

    /**
     * @see #asCheckedCollection(Object, Class, Collection)
     */
    public static <T> List<T> asCheckedArrayList(final Object listObject, final Class<T> valueClassType) {
        return asCheckedCollection(listObject, valueClassType, new ArrayList<>());
    }

    /**
     * @see #asCheckedCollection(Object, Class, Collection)
     */
    public static <T> List<T> asCheckedLinkedList(final Object listObject, final Class<T> valueClassType) {
        return asCheckedCollection(listObject, valueClassType, new LinkedList<>());
    }

    /**
     * @see #asCheckedCollection(Object, Class, Collection)
     */
    public static <T> Set<T> asCheckedHashSet(final Object setObject, final Class<T> valueClassType) {
        return asCheckedCollection(setObject, valueClassType, new HashSet<>());
    }

    /**
     * @see #asCheckedCollection(Object, Class, Collection)
     */
    public static <T> Set<T> asCheckedLinkedHashSet(final Object setObject, final Class<T> valueClassType) {
        return asCheckedCollection(setObject, valueClassType, new LinkedHashSet<>());
    }

    /**
     * @see #asCheckedMap(Object, Class, Class, Map)
     */
    public static <K, V> Map<K, V> asCheckedHashMap(final Object mapObject, final Class<K> keyClassType, final Class<V> valueClassType) {
        return asCheckedMap(mapObject, keyClassType, valueClassType, new HashMap<>());
    }

    /**
     * @see #asCheckedMap(Object, Class, Class, Map)
     */
    public static <K, V> Map<K, V> asCheckedLinkedHashMap(final Object mapObject, final Class<K> keyClassType, final Class<V> valueClassType) {
        return asCheckedMap(mapObject, keyClassType, valueClassType, new LinkedHashMap<>());
    }
}