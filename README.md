# Collection Type-safe Converter
Helper library that generates type-safe generic shallow copies from: 
* Raw non-generic collection objects
* Unknown type generic collections

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
    compile("io.github.azagniotov:collection-type-safe-converter:1.0.0")
}
```

### Usage

Check the `TypeSafeConverter` class for the available APIs.


```
final Map<String, SomeType> checkedMap = TypeSafeConverter.asCheckedHashMap(rawMapObject, String.class, SomeType.class);
final Map<String, SomeType> checkedMap = TypeSafeConverter.asCheckedMap(rawMapObject, String.class, SomeType.class, new LinkedHashMap<>());
final Set<SomeType> checkedSet = TypeSafeConverter.asCheckedLinkedHashSet(rawSetObject, SomeType.class);
```

### License
MIT
