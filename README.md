# Type-safe Generic Collections
Helper library that generates type-safe generic shallow copies from raw non-generic collections

## Summary
 1. The library guarantees type safety of the values, which eliminates heap pollution
 2. The need for `@SuppressWarnings("unchecked")` annotation is eliminated in the client code
 3. Since generating a new type-safe collection has a `O(N)` running time, please execute caution before deciding to convert large collections
