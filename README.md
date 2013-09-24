CollectionMap
=============

`CollectionMap` is an implementation of a Map that allows you to store multiple values for a single key. This is done by using a Map that keeps Collections of the type you actually want to store.

Usage
-----
You have to subclass `CollectionMap` and implement `createContentsMap()` and `createCollection()`. The former will be called by the constructor to create the Map that will hold all values. `createCollection()` will be called whenever a value is added to a key that has been empty. If it returns a thread safe implemention of `Collection` then `CollectionMap` will also be thread safe.
