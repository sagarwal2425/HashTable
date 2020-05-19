# Do-you-want-to-implement-HashTable?
Implementation of HashTable using Collision resolution by Open-addressing.

Hashtable is a data structure that implements an associative array abstract data type, a structure that can map keys to values. A hashtable uses a hash function to compute an index, also called a hash code, into an array of buckets or slots, from which the desired value can be found. In hashtable collision is a situation that occurs when two distinct pieces of data have the same hash value. Hence in order to avoid collisions in hashtable we use a process called collision resolution by open addressing, in which all elements are stored in the hash table itself. So at any point, size of the table must be greater than or equal to the total number of keys. Open Addressing is done following ways:

a) 
# Linear Probing: 
In linear probing, we linearly probe for next slot.

b) 
# Quadratic Probing: 
We look for i2‘th slot in i’th iteration.

This implementation is done using Linear Probing.

# Steps for Compilation and execution of HashTable program:

javac hashTable.java	//Compilation of HashTable program

java hashTable samplefile.txt	//Execution of Hashtable program
