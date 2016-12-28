# Collection Type-safe Converter
Helper library that generates type-safe generic shallow copies from: 
* Raw non-generic collections; and
* Generic collections of unknown types

## Summary
 1. The library guarantees type safety of the values, which eliminates heap pollution
 2. The need for `@SuppressWarnings("unchecked")` annotation is eliminated in the client code
 3. Since generating a new type-safe collection has a `O(N)` running time, please execute caution before deciding to convert large collections

### Dependencies

Gradle

```
repositories {
    mavenCentral()
}

dependencies { 
    compile("io.github.azagniotov:collection-type-safe-converter:1.0.1")
}
```

### Usage

Check the [TypeSafeConverter](src/main/java/io/github/azagniotov/generics/TypeSafeConverter.java) class for the available APIs.

#### Some examples
```
final Map<String, SomeType> checkedMap = TypeSafeConverter.asCheckedHashMap(rawMapObject, String.class, SomeType.class);
final Map<String, SomeType> checkedMap = TypeSafeConverter.asCheckedMap(rawMapObject, String.class, SomeType.class, new LinkedHashMap<>());
final Set<SomeType> checkedSet = TypeSafeConverter.asCheckedLinkedHashSet(rawSetObject, SomeType.class);
final Set<SomeType> checkedSet = TypeSafeConverter.asCheckedCollection(rawSetObject, SomeType.class, new HashSet<>());
final Iterable<AnotherType> checkedSet = TypeSafeConverter.asCheckedIterable(rawSetObject, AnotherType.class);
final SomeType someType = TypeSafeConverter.as(SomeType.class, objectInstance)
```

### License
MIT
