# Summary of Information Coding

## Basic Concepts

### Alphabets, Words, and Languages
- **Alphabet**: A non-empty set of characters, letters, or symbols
- **Word**: A finite sequence of symbols from an alphabet
- **Word Length**: The number of symbols in a word, denoted as |u|
- **Empty Word**: Denoted as ε, has length 0
- **Concatenation**: Operation (u.v) that joins two words to form a new one
  - Properties: Associative, has empty word as neutral element, non-commutative

### Prefixes and Suffixes
- **Prefix**: Word v is a prefix of u if u = v.w for some word w
- **Suffix**: Word v is a suffix of u if u = w.v for some word w

### Languages
- **Language**: Any subset of A*, where A* is the set of all possible words from alphabet A
- Examples:
  - Words without two consecutive 1's
  - Words formed by concatenating 0 and/or 01
  - Words of the form 0ⁿ1ⁿ where n ≥ 0

## Codes and Coding

### Word Factorization
- A word u can be factorized in a language L if u = u₁u₂...uₙ where each uᵢ is in L
- Factorization may not be unique, or may not exist at all

### Codes
- **Code**: A language where each word has at most one factorization
- Examples:
  - Any alphabet A is a code
  - {0, 01} is a code
  - {10ⁿ, n ∈ N} is a code
  - {0, 10, 01} is not a code (e.g., 010 can be factorized as 0.10 or 01.0)
  - Any language containing the empty word is not a code

### Coding Systems
- **Coding**: A mapping μ: S* → A* where:
  1. μ is injective (different words have different codes)
  2. μ preserves concatenation (μ(u.v) = μ(u).μ(v))
  3. μ maps the empty word to itself (μ(ε) = ε)

## Examples of Coding Systems

### Morse Code
- Maps letters to patterns of dots (•), dashes (−), and spaces (_)
- Example: "ITU" = •• _ − _ ••−
- Uses spacing conventions: 1 space between symbols, 3 spaces between letters, 5 spaces between words

### ASCII Code
- Maps 128 characters to 7-bit binary numbers
- Example: "ITU" = 1001001 1010100 1010101

### Other Coding Systems
- Baudot code
- Manchester code
- Miller code
- ISO-8859
- UTF-8

## Key Properties

- Prefix codes are always codes (except the empty set)
- The minimum length of code words for an alphabet of size n is ⌈log₂(n)⌉
  - For Latin alphabet (26 letters), minimum length is 5 bits