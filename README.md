# Type-safe Generic Collections
Helper library that generates type-safe generic shallow copies from raw non-generic collections

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
    compile("io.github.azagniotov:type-safe-generic-collections:1.0.0")
}
```

### Usage

Check the `TypeSafetyHelper` class for the available APIs.


```
final Map<String, SomeType> checkedMap = TypeSafetyHelper.asCheckedHashMap(rawMapObject, String.class, SomeType.class);
final Map<String, SomeType> checkedMap = TypeSafetyHelper.asCheckedMap(rawMapObject, String.class, SomeType.class, new LinkedHashMap<>());
final Set<SomeType> checkedSet = TypeSafetyHelper.asCheckedLinkedHashSet(rawSetObject, SomeType.class);
```

### License
MIT
