# Summary of Code Demonstration

## Proving a Language is a Code

### Key Concept
To demonstrate that a language is a code, we often use **proof by contradiction**.

### Definition Reminder
A code is a language where every word has at most one factorization.

### Example Demonstration
**Proving L = {0, 01} is a code:**

1. **Assumption for contradiction**: Assume L is not a code, meaning there exists a word u with two different factorizations:
   - u = u₁u₂...uₚ
   - u = u'₁u'₂...u'ₜ
   
   where all uᵢ and u'ᵢ are elements of L.

2. **Key observation**: There must be a position x where:
   - uᵢ = u'ᵢ for all i < x
   - uₓ ≠ u'ₓ

3. **Consequence**: Since uₓ ≠ u'ₓ but their concatenations lead to the same word, either uₓ is a prefix of u'ₓ or vice versa.

4. **Analysis**: If uₓ is a prefix of u'ₓ, then:
   - uₓ must be 0
   - u'ₓ must be 01
   - This leads to: 0uₓ₊₁...uₚ = 01u'ₓ₊₁...u'ₜ
   - Which means: uₓ₊₁...uₚ = 1u'ₓ₊₁...u'ₜ
   - This implies uₓ₊₁ begins with 1

5. **Contradiction**: None of the elements in L = {0, 01} begin with 1, which contradicts our assumption.

6. **Conclusion**: Our assumption is false, so L is indeed a code.

### Important Notes
- If a word has more than two factorizations, we can analyze them two at a time
- The proof works symmetrically if we assume u'ₓ is a prefix of uₓ instead

### Practice Problems
Languages to prove whether they are codes or not:
- L = {000, 010, 011, 01001}
- L = {00, 01, 110, 001}
- L = ∅ (empty set)
- L = {ε} (contains only the empty word)
- L = {0011, 1001, 0110}
- L = {010101, 0101}