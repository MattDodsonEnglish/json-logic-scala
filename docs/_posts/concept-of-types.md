---
title: "Concept of Type for Parsing/Unparsing"
author: Charles
nav_order: 3
category: Jekyll
layout: post
---

# Behind the concept of "Type" in Json-Logic-Typed

Json Logic Typed is a variant of the jsonLogic format that annotates data-type
values.

When using statically typed languages like Scala,
providing type information lets an application choosing the right data structure
at parsing time.

Consider the following example:

```json
{
  "value1": 45
}
```

This JSON snippet can be easily parsed in a dynamically-typed language like
Python, Perl, or Javascript.
In these languages, the implementation doesn't need to know in advance the type
behind the key `value1`.

On the other hand, in typed languages like Scala, the implementation
needs to know in advance the types in the JSON fields.

Json-logic-Scala accepts only JSON in  Json-Logic-Typed format.
To address this drag, json-logic-scala defines a Scala data structure that you
can use to parse a JSON field or value according to its annotated Type.

## How to annotate type in Json-Logic-Typed format

Type is annotated after the field "type" in a "var" operator JSON
(i.e. the leaf node in corresponding syntax tree).

### Simple Type

A simple type is simply defined by its `codename` field value.

```json
[{
    "...": [
            {"var":  "price_value", "type":  {"codename":  "int"}}
    ]
},
{
    "price_value": ...
}]
```

### Higher Type (option|array|map)
A higher type is a composition of simple and/or higher types.
A higher Type represents generic types in Scala such as: Array, Option, Map.

It is recursively defined by its "codename" field value and its "paramType" field value.

Example 1:
In the following example, variable "price_values" is to be parsed as an `Array[Int]`
```json
[{
    "...": [
            {"var":  "price_values", "type":  {"codename":  "array", "paramType": {"codename":  "int"}}}
    ]
},
{
    "price_values": ...
}]
```

Example 2:
Higher Types can be composed.
In the following example, variable "category_to_price_values" is to be parsed as an `Map[String, Array[Int]]`
```json
[{
    "...": [
            {"var":  "category_to_price_values", "type":  {"codename":  "map", "paramType": {"codename":  "array", "paramType": {"codename":  "int"}}}}
    ]
},
{
    "category_to_price_values": ...
}]
```

## Default types in Json Logic Scala
Json Logic Scala comes with a number of built-in defined Types to avoid you to reinvent the wheel.

| Type  | Scala data structure  | is Higher Type    |
|---|---|---|
|   "boolean"   |    `Boolean`   |  no  |
|   "int"       |    `Int`       |  no  |
|   "long"      |    `Long`      |  no  |
|   "float"     |    `Float`     |  no  |
|   "double"    |    `Double`    |  no  |
|   "string"    |    `String`    |  no  |
|   "short"     |    `Short`     |  no  |
|   "byte"      |    `Byte`      |  no  |
|   "null"      |    `Null`      |  no  |
|   "option"     |    `Option`     |  yes  |
|   "array"      |    `Array`      |  yes  |
|   "map"      |    `Map`      |  yes  |

Higher Types require "paramType" field to be defined in json.

**This means that if you stick to those codenames for Type annotation in your json, you can simply use
default `Serializer`/`Deserializer` to parse/unparse your json.**