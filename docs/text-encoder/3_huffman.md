# Summary of Huffman Coding

## Information Theory Basics

Huffman coding is a clever way to compress data by using fewer bits for common symbols and more bits for rare ones. Think of it like Morse code, where frequently used letters get shorter codes.

-> The Basic Idea
Imagine you're sending a text message and want to save space:

If the letter 'e' appears a lot, you give it a short code (like "0")
If the letter 'z' is rare, you give it a longer code (like "1010")

This way, your total message becomes shorter because common elements take less space.

-> How It Works

Count how often each symbol appears (like counting letters in a text)
Build a special tree by repeatedly connecting the two least common symbols
Create codes by labeling the branches of the tree with 0s and 1s
Read the codes by following the path from the top of the tree to each symbol

-> Why It's Special

It's optimal - No other variable-length code can compress the data better
It's uniquely decodable - There's no confusion when decoding because no code is the prefix of another code
It approaches the theoretical limit (called entropy) of how much compression is possible

-> Real-World Use
Huffman coding is used in:
  ZIP and GZIP file compression
  JPEG image compression
  MP3 audio compression
  Data transmission to save bandwidth

### Information Source
- An information source has:
  - An alphabet S = {s₁, s₂, ..., sₘ} of symbols it can emit
  - A probability distribution P = {p₁, p₂, ..., pₘ} showing how likely each symbol is to appear

### Information Quantity
- The information content of a symbol s is: I(s) = -log₂(p), where p is the probability of s
- Unit of information: bit
- Rare symbols contain more information than common ones

### Entropy
- Entropy H(S) is the average information content of a source
- Formula: H(S) = -∑(pᵢ × log₂(pᵢ))
- Represents the theoretical minimum average bits needed to encode the source
- Ranges between 0 ≤ H(S) ≤ log₂(m)

## Optimal Coding

### Average Code Length
- The average code length of a coding scheme is: n̄ = ∑(pᵢ × |c(sᵢ)|)
- Where |c(sᵢ)| is the length of the code for symbol sᵢ

### Optimal Code
- A code is optimal if no other code for the same source has a shorter average length

### Shannon's Noiseless Coding Theorem
- For an optimal code with target alphabet of size q:
  - H(S)/log₂(q) ≤ n̄ < H(S)/log₂(q) + 1
- This means the average code length is bounded by the entropy

## Huffman Coding

### Concept
- Variable-length prefix code that assigns shorter codes to more frequent symbols
- Creates an optimal binary prefix code for any source

### Algorithm to Build Huffman Tree
1. Create a leaf node for each symbol with its probability
2. Find the two nodes with lowest probabilities
3. Create a new internal node with these two nodes as children
4. Assign probability to this new node equal to the sum of the two child nodes
5. Repeat steps 2-4 until only one node remains (the root)
6. Assign 0 to left branches and 1 to right branches
7. The code for each symbol is the sequence of 0s and 1s on the path from root to that symbol's leaf

### Example
- For a source with symbols and probabilities:
  - s₁: 0.30, s₂: 0.20, s₃: 0.15, s₄: 0.15, s₅: 0.10, s₆: 0.10
- The Huffman coding gives codes:
  - s₁: 11, s₂: 01, s₃: 101, s₄: 100, s₅: 001, s₆: 000
- The average code length is 2.5 bits
- The source entropy is approximately 2.471 bits

### Applications
- Data compression
- File compression algorithms
- Text encoding