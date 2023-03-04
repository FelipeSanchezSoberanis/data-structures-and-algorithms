<!-- vim-markdown-toc Marked -->

* [Asymptotic notation](#asymptotic-notation)
* [Big-O notation](#big-o-notation)
* [Data structures](#data-structures)
    * [Arrays](#arrays)
        * [Times for common operations](#times-for-common-operations)
    * [Linked lists](#linked-lists)
        * [Times for common operations](#times-for-common-operations)
    * [Double linked lists](#double-linked-lists)
        * [Times for common operations](#times-for-common-operations)
    * [Stacks](#stacks)
    * [Queues](#queues)
    * [Trees](#trees)
        * [Walking a tree](#walking-a-tree)
    * [Dynamic arrays](#dynamic-arrays)

<!-- vim-markdown-toc -->

# Asymptotic notation

log n < sqrt n < n < n log n < n^2 < 2^n

# Big-O notation

- Clarifies growth rate
- Cleans up notation

# Data structures

## Arrays

- Contiguous area of memory consisting of equal-size elements indexed by contiguous integers
- Constant-time access to any element
- Constant time to add/remove at the end
- Linear time to add/remove at an arbitrary location

### Times for common operations

|           | Add  | Remove |
| ---       | ---  | ---    |
| Beginning | O(n) | O(n)   |
| End       | O(1) | O(1)   |
| Middle    | O(n) | O(n)   |

## Linked lists

### Times for common operations

| ---                  | No tail | Tail |
| ---                  | ---     | ---- |
| PushFront(key)       | O(1)    |      |
| TopFront()           | O(1)    |      |
| PopFront()           | O(1)    |      |
| PushBack(key)        | O(n)    | O(1) |
| TopBack(key)         | O(n)    | O(1) |
| PopBack(key)         | O(n)    |      |
| Find(key)            | O(n)    |      |
| Erase(key)           | O(n)    |      |
| Empty()              | O(1)    |      |
| AddBefore(node, key) | O(n)    |      |
| AddAfter(node, key)  | O(1)    |      |

## Double linked lists

### Times for common operations

| ---                  | No tail | Tail |
| ---                  | ---     | ---- |
| PushFront(key)       | O(1)    |      |
| TopFront()           | O(1)    |      |
| PopFront()           | O(1)    |      |
| PushBack(key)        | O(n)    | O(1) |
| TopBack(key)         | O(n)    | O(1) |
| PopBack(key)         | O(1)    |      |
| Find(key)            | O(n)    |      |
| Erase(key)           | O(n)    |      |
| Empty()              | O(1)    |      |
| AddBefore(node, key) | O(1)    |      |
| AddAfter(node, key)  | O(1)    |      |

## Stacks

- Abstract data type with the following operations:
    - Push(key): adds key to the collection
    - Key Top(): returns most recently-added key
    - Key Pop(): removes and returns the most recently-added key
    - Boolean Empty(): are there any elements?
- Can be implemented with either an array or a linked list
- Each stack operation is O(1)
- Stacks are ocassionaly known as LIFO queues

Example usage: find balanced brackets in a string

## Queues

- Abstract data type with the following operations:
    - Enqueue(key): adds key to the collection
    - Key Dequeue(): removes and returns least recently-added key
    - Boolean Empty(): are there any elements?
- Queues can be implemented with either a linked list with a tail pointer or and array
- Each queue operation is O(1): Enqueue, Dequeue, Empty

## Trees

- Trees are used for lots of different things
- Trees have a key and children
- Tree walks: DFS and BFS
- When working with a tree, recursive algorithms are common
- In computer science, trees grow down

- A tree is:
    - empty, or
    - a node with:
        - a key, and
        - a list of child trees

In a tree, the information in a node is:
- Key
- Children: list of children nodes
- (Optional) parent node

For binary trees, the node contains:
- Key
- Left
- Right
- (Optional) parent

### Walking a tree

Often, we want to visit the nodes of a tree in a particular order.

For example, print the nodes of the tree.

- Depth first: we completely traverse one sub-tree before exploring a sibling sub-tree
- Breadth-first: we traverse all nodes at one level before progressing to the next level

## Dynamic arrays

Problem: might not know max size when allocating an array.

Solution: dynamic arrays (also known as resizable arrays).

Idea: store a pointer to a dinamically allocated array, and replace it with a newly-allocated array as needed.

It is an abstract data type with the following operations (at least):
- Get(i): return element at location i
- Set(i, val): sets element i to val
- PushBack(val): adds val to the end
- Remove(i): removes element at location i
- Size(): return the number of elements

To implement, you have to store:
- arr: dynamically-allocated array
- capacity: size of the dynamically-allocated array
- size: number of elements currently in the array

