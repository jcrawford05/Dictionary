# Dictionary Performance Analysis

This document summarizes the performance characteristics of a Java-based
dictionary application. The goal of the analysis was to evaluate whether the
chosen data structures support efficient execution of core dictionary
operations as the dataset size increases.

The implementation emphasizes fast lookup, ordered traversal, and consistent
behavior under mutation (add, update, remove).

---

## Data Structures and Design Rationale

The dictionary is implemented using a layered design that separates concerns
between words, definitions, and metadata.

### DictionaryDefinition

Each definition–part-of-speech pair is represented by a dedicated
`DictionaryDefinition` object. Valid parts of speech are tracked using a
`HashSet`, allowing constant-time validation when adding or updating
definitions.

This class also provides comparison and formatting behavior to support sorted
storage and clean output.

---

### DictionaryWord

Each word is represented by a `DictionaryWord` object containing:

- A `TreeSet<DictionaryDefinition>` storing definitions
- A `TreeSet<String>` tracking the parts of speech used by the word

TreeSets were chosen to:
- Prevent duplicate definitions
- Maintain sorted order
- Support efficient insertion, removal, and lookup

---

### Dictionary

The dictionary itself is built using a combination of structures:

- `TreeMap<String, DictionaryWord>` for ordered word storage
- `HashSet<String>` for fast existence checks
- `HashMap<String, Integer>` to track part-of-speech frequencies

This design balances fast access with ordered traversal while keeping metadata
queries efficient.

---

### Main Driver

The main driver handles user interaction and file I/O. Each menu option
delegates directly to a dictionary operation, keeping control flow explicit and
easy to follow.

---

## Timing Experiment Methodology

Timing experiments were conducted on the following operations:

- Adding a new word
- Adding a new definition
- Updating an existing definition
- Removing a definition

### Experimental Setup

- Dictionary sizes ranged from 10,000 to over 100,000 entries
- Input size increased in fixed increments
- Each measurement was averaged across multiple iterations
- Randomized inputs were used to avoid biased access patterns

Separate initialization logic was used for add-only experiments versus update
and removal experiments to reflect realistic usage.

---

## Experimental Results and Analysis

### Add Word

**Expected complexity:** O(log N)

Observed runtimes closely follow logarithmic growth. This aligns with the use
of a `TreeMap`, where insertion requires maintaining sorted order. The results
confirm that the structure scales well as the dictionary grows.

---

### Add Definition

**Expected complexity:** O(definitions per word)

Measured runtimes fall between constant and logarithmic time. This behavior is
expected, as performance depends on the size of the definition set associated
with a word rather than total dictionary size.

---

### Update Definition

**Expected complexity:** O(definitions per word)

Results mirror the add-definition experiment. Runtime increases with the number
of definitions attached to a word, but remains independent of total dictionary
size.

---

### Remove Definition

**Expected complexity:**
- O(definitions per word) when only a definition is removed
- O(log N) when removing the final definition also removes the word

The timing data reflects this dual behavior. Removing the last definition
incurs the cost of updating the underlying `TreeMap`, producing logarithmic
growth.

---

## Space–Time Tradeoffs

The implementation favors time efficiency over minimal memory usage. Several
structures intentionally duplicate information:

- Words stored in both maps and sets
- Parts of speech tracked independently from definitions

This redundancy enables faster metadata queries, range searches, and validation
checks. Given the performance focus of the project, this tradeoff was deemed
appropriate.

---

## Limitations and Possible Improvements

- Alternative data structures could reduce overhead further
- Memory usage could be lowered by consolidating overlapping structures
- Additional abstraction in the main driver could improve extensibility

Within the constraints of the project, the design achieves strong performance
while remaining readable and maintainable.

---

## Conclusion

The timing experiments confirm that the dictionary implementation behaves as
intended. Most operations scale sublinearly with dictionary size, and observed
runtimes closely match theoretical expectations based on the chosen data
structures.

The final design strikes a practical balance between efficiency, clarity, and
extensibility, making it suitable for both interactive use and performance
analysis.
