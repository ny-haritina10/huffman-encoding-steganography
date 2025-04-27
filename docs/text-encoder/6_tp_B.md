# Text File Compression with Huffman Coding in Java (OOP Approach)

Detailed step-by-step guide to implement file compression and decompression using Huffman coding in Java with an object-oriented approach:

## Implementation considerations

### File format design
- Make your header format clear and easy to parse
- Consider including metadata like original file size, checksum, etc.
- Format example:
  ```
  [Magic number: "HUFF"]
  [Number of unique characters: 4 bytes]
  [For each character:
    - Character code: 1-4 bytes
    - Huffman code length: 1 byte
    - Huffman code: variable bits]
  [Compressed data length: 4 bytes]
  [Compressed data: variable bits]
  [Padding bits in last byte: 1 byte]
  ```

### Bit manipulation techniques
- Create utility methods for bit operations:
  - Writing individual bits to an output stream
  - Reading individual bits from an input stream
  - Converting between bit strings and packed bytes

### Error handling
- Handle file I/O exceptions properly
- Validate the compressed file format during decompression
- Add integrity checks for the decompressed data

### Performance optimization
- Use buffered I/O for better performance
- Consider multithreading for large files:
  - One thread for reading
  - One thread for encoding/decoding
  - One thread for writing

### Memory management
- For large files, process the data in chunks
- Avoid loading the entire file into memory at once
- Use efficient data structures for the Huffman tree and code tables

## Question 2(a): Compress text file using Huffman coding

### Step 1: Design the compression architecture
- Create a `FileCompressor` class to handle the overall compression process
- Make it use the `HuffmanCoder` class from the previous assignment

### Step 2: Read and process the text file
- Create a method to read the text file (texte.txt) into memory
- Use BufferedReader or FileInputStream for efficient file reading
- Consider handling large files in chunks if memory is a concern

### Step 3: Generate frequency table
- Count character frequencies in the text
- Store frequencies in a data structure (HashMap<Character, Integer>)

### Step 4: Create the Huffman tree and generate codes
- Use the previously created HuffmanTree class to build the tree
- Generate the Huffman codes for each character

### Step 5: Prepare the compressed file format
- Design a file format that includes:
  1. Header with the Huffman code table (character-to-code mapping)
  2. The compressed data

### Step 6: Write the header (character codes)
- Determine an efficient way to store the code table
- Format options:
  - Character, code length, code (for each character)
  - Character frequency pairs (to rebuild the tree during decompression)
  - Serialized tree structure

### Step 7: Write the compressed data
- Convert the text to its Huffman-encoded form
- Pack the bits efficiently (8 bits per byte)
- Implement bit manipulation to save the encoded data as actual bits rather than "0" and "1" characters
- Handle the last byte which might be partially filled

### Step 8: Finalize and close the compressed file
- Add any necessary end-of-file markers or padding information
- Close all file resources properly



## Question 2(b): Decompress the compressed file

### Step 1: Design the decompression architecture
- Create a `FileDecompressor` class to handle the overall decompression process

### Step 2: Open and parse the compressed file
- Read the file header to extract the Huffman code table
- Parse the code table to either:
  - Build a direct mapping of codes to characters
  - Reconstruct the Huffman tree

### Step 3: Create a decoding structure
- Option 1: Build a lookup table (HashMap<String, Character>)
- Option 2: Reconstruct the Huffman tree for traversal-based decoding
  - Create nodes based on the saved codes or frequencies
  - Rebuild the tree structure

### Step 4: Read the compressed data
- Read the bit stream from the file
- Use bit manipulation to extract individual bits from bytes

### Step 5: Decode the data
- If using a lookup table:
  - Accumulate bits until a valid code is found
  - Output the corresponding character
  - Clear the accumulated bits and continue
- If using the tree:
  - Start at the root
  - Follow left child for 0, right child for 1
  - When reaching a leaf, output the character and restart from root

### Step 6: Write the decompressed text
- Create an output file for the decompressed text
- Write the decoded characters to this file
- Ensure proper handling of text encoding (UTF-8, etc.)

### Step 7: Finalize decompression
- Close all file resources
- Verify decompression was successful