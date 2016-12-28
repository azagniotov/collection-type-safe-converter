package io.github.azagniotov.generics;

import io.github.azagniotov.generics.helpers.AnotherType;
import io.github.azagniotov.generics.helpers.SomeType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.github.azagniotov.generics.TypeSafeConverter.asCheckedCollection;
import static io.github.azagniotov.generics.TypeSafeConverter.asCheckedIterable;
import static io.github.azagniotov.generics.TypeSafeConverter.asCheckedMap;


@SuppressWarnings("unchecked")
public class TypeSafeConverterTest {

    private static final SomeType INSTANCE_SOME_TYPE = new SomeType("8");
    private static final AnotherType INSTANCE_ANOTHER_TYPE = new AnotherType(9L);
    private static final Object RAW_INSTANCE_SOME_TYPE = new SomeType("6");

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void shouldCheckCast() throws Exception {
        TypeSafeConverter.checkCast(SomeType.class, INSTANCE_SOME_TYPE);
    }

    @Test
    public void shouldThrowWhenCheckCast() throws Exception {
        expectedException.expect(ClassCastException.class);
        expectedException.expectMessage("Expected: io.github.azagniotov.generics.helpers.SomeType, instead got: io.github.azagniotov.generics.helpers.AnotherType");

        TypeSafeConverter.checkCast(SomeType.class, INSTANCE_ANOTHER_TYPE);
    }

    @Test
    public void shouldThrowWhenPassingNullCheckCast() throws Exception {
        expectedException.expect(ClassCastException.class);
        expectedException.expectMessage("Expected: io.github.azagniotov.generics.helpers.SomeType, instead got: io.github.azagniotov.generics.helpers.AnotherType");

        TypeSafeConverter.checkCast(SomeType.class, INSTANCE_ANOTHER_TYPE);
    }

    @Test
    public void shouldCastAs() throws Exception {
        final SomeType casted = TypeSafeConverter.as(SomeType.class, RAW_INSTANCE_SOME_TYPE);
    }

    @Test
    public void shouldThrowWhenCastAsWrongType() throws Exception {
        expectedException.expect(ClassCastException.class);
        expectedException.expectMessage("Expected: io.github.azagniotov.generics.helpers.AnotherType, instead got: io.github.azagniotov.generics.helpers.SomeType");

        TypeSafeConverter.as(AnotherType.class, RAW_INSTANCE_SOME_TYPE);
    }

    @Test
    public void shouldCastCheckedListWhenRawHomogeneousList() throws Exception {
        final List rawHomogeneousList = new ArrayList();
        rawHomogeneousList.add(new HashSet(Collections.singletonList(INSTANCE_SOME_TYPE)));
        rawHomogeneousList.add(new HashSet(Collections.singletonList(INSTANCE_SOME_TYPE)));

        final List<HashSet> checkedList = asCheckedCollection(rawHomogeneousList, HashSet.class, new ArrayList<>());
    }

    @Test
    public void shouldThrowWhenCastCheckedListWhenRawHomogeneousListWithNullValues() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Object instance is null");

        final List rawHomogeneousList = new ArrayList();
        rawHomogeneousList.add(null);
        rawHomogeneousList.add(new HashSet(Collections.singletonList(INSTANCE_SOME_TYPE)));

        asCheckedCollection(rawHomogeneousList, HashSet.class, new ArrayList<>());
    }

    @Test
    public void shouldThrowWhenCastCheckedListWhenRawHomogeneousListWithWrongValueClass() throws Exception {
        expectedException.expect(ClassCastException.class);

        final List rawHomogeneousList = new ArrayList();
        rawHomogeneousList.add(new HashSet<>(Collections.singletonList(INSTANCE_SOME_TYPE)));
        rawHomogeneousList.add(new HashSet<>(Collections.singletonList(INSTANCE_SOME_TYPE)));

        final List<SomeType> checkedList = asCheckedCollection(rawHomogeneousList, SomeType.class, new ArrayList<>());
    }

    @Test
    public void shouldThrowWhenCastCheckedListWhenRawHeterogeneousList() throws Exception {
        expectedException.expect(ClassCastException.class);

        final List rawHomogeneousList = new ArrayList();
        rawHomogeneousList.add(INSTANCE_SOME_TYPE);
        rawHomogeneousList.add(INSTANCE_ANOTHER_TYPE);

        final List<SomeType> checkedList = asCheckedCollection(rawHomogeneousList, SomeType.class, new ArrayList<>());
    }

    @Test
    public void shouldCastCheckedSetWhenRawHomogeneousSet() throws Exception {
        final Set rawHomogeneousSet = new HashSet();
        rawHomogeneousSet.add(new HashSet(Collections.singletonList(INSTANCE_SOME_TYPE)));
        rawHomogeneousSet.add(new HashSet(Collections.singletonList(INSTANCE_SOME_TYPE)));

        final Set<HashSet> checkedSet = asCheckedCollection(rawHomogeneousSet, HashSet.class, new HashSet<>());
    }

    @Test
    public void shouldThrowWhenCastCheckedSetWhenRawHomogeneousSetWithNullValues() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Object instance is null");

        final Set rawHomogeneousSet = new HashSet();
        rawHomogeneousSet.add(null);
        rawHomogeneousSet.add(new HashSet(Collections.singletonList(INSTANCE_SOME_TYPE)));

        asCheckedCollection(rawHomogeneousSet, HashSet.class, new HashSet<>());
    }

    @Test
    public void shouldThrowWhenCastCheckedSetWhenRawHomogeneousSetWithWrongValueClass() throws Exception {
        expectedException.expect(ClassCastException.class);

        final Set rawHomogeneousSet = new HashSet();
        rawHomogeneousSet.add(INSTANCE_SOME_TYPE);
        rawHomogeneousSet.add(INSTANCE_SOME_TYPE);

        final Iterable<AnotherType> checkedSet = asCheckedIterable(rawHomogeneousSet, AnotherType.class);
    }

    @Test
    public void shouldCastCheckedMapWhenRawHomogeneousMap() throws Exception {
        final Map rawHomogeneousMap = new HashMap();
        rawHomogeneousMap.put(INSTANCE_SOME_TYPE.getValue(), new HashSet<>(Collections.singletonList(INSTANCE_SOME_TYPE)));
        rawHomogeneousMap.put(INSTANCE_SOME_TYPE.getValue(), new HashSet<>(Collections.singletonList(INSTANCE_SOME_TYPE)));

        final Map<String, HashSet> checkedMap = asCheckedMap(rawHomogeneousMap, String.class, HashSet.class, new LinkedHashMap<>());
    }

    @Test
    public void shouldThrowWhenCastCheckedMapWhenRawHomogeneousMapWithWrongValueClass() throws Exception {
        expectedException.expect(ClassCastException.class);

        final Map rawHomogeneousMap = new HashMap();
        rawHomogeneousMap.put(INSTANCE_SOME_TYPE.getValue(), new HashSet<>(Collections.singletonList(INSTANCE_SOME_TYPE)));
        rawHomogeneousMap.put(INSTANCE_SOME_TYPE.getValue(), new HashSet<>(Collections.singletonList(INSTANCE_SOME_TYPE)));

        final Map<String, SomeType> checkedMap = asCheckedMap(rawHomogeneousMap, String.class, SomeType.class, new LinkedHashMap<>());
    }

    @Test
    public void shouldThrowWhenCastCheckedMapWhenRawHeterogeneousMap() throws Exception {
        expectedException.expect(ClassCastException.class);

        final Map rawHeterogeneousMap = new HashMap();
        rawHeterogeneousMap.put(INSTANCE_SOME_TYPE.getValue(), INSTANCE_SOME_TYPE);
        rawHeterogeneousMap.put(INSTANCE_ANOTHER_TYPE.getValue(), INSTANCE_ANOTHER_TYPE);

        final Map<String, SomeType> checkedMap = asCheckedMap(rawHeterogeneousMap, String.class, SomeType.class, new LinkedHashMap<>());
    }
}
