# Dictionary Application (Java)

This project is a Java-based dictionary application that supports fast word
lookup, range queries, definition management, and metadata reporting. The
implementation emphasizes efficient data structures and predictable runtime
behavior as the dictionary grows.

The repository includes a browser-based demo, the complete Java source code,
and a separate performance analysis of core dictionary operations.

---

## Live Demo

A static, browser-based demo that mirrors the behavior of the original Java
console application is available here:

**Live Demo:**  
https://jcrawford05.github.io/Dictionary/

The demo reproduces the menu-driven interface and core functionality so the
project can be explored without compiling or running Java code.

---

## Demo Notes

- Implemented using HTML, CSS, and JavaScript
- Dictionary data is embedded for static execution
- All menu options are functional and mirror the Java program’s behavior
- Intended for functional exploration, not performance benchmarking

Performance measurements apply to the Java implementation and are documented
separately.

---

## Source Code

The Java implementation lives in the `comprehensive/` directory and includes:

### Core Dictionary Implementation
- `Dictionary` — primary data structure and operations
- `DictionaryWord` — word-level storage and definition management
- `DictionaryDefinition` — definition and part-of-speech representation
- `Main` — menu-driven console application entry point

### Timing Experiments
Separate drivers were written to measure the performance of individual
dictionary operations:

- `DictionaryAddTimingExperiment`
- `DictionaryAddDefTimingExperiment`
- `DictionaryUpdateTimingExperiment`
- `DictionaryRemoveTimingExperiment`


These experiments evaluate how runtime scales as dictionary size increases.

### Data Files
Several input files are included for testing and benchmarking:

- `1000_word_definitions.txt`
- `2420_glossary-1.txt`

---

## Performance Analysis

Detailed timing experiments and analysis of dictionary operations are documented
in a separate report:

- [Timing Analysis](Dictionary/TIMING_ANALYSIS.md)

This report evaluates runtime behavior, compares observed performance against
theoretical expectations, and discusses design tradeoffs.

---

## Repository Structure

```text
/docs
  index.html            Browser-based console demo
  style.css             Demo styling
  terminal.js           Demo logic

/comprehensive
  Dictionary.java
  DictionaryWord.java
  DictionaryDefinition.java
  Main.java
  DictionaryAddTimingExperiment.java
  DictionaryAddDefTimingExperiment.java
  DictionaryUpdateTimingExperiment.java
  DictionaryRemoveTimingExperiment.java
  TimingExperiment.java
  *.txt                 Test and benchmark data files

README.md               Project overview
TIMING_ANALYSIS.md      Performance analysis
