# ZedmeeHash 32/64
Strong, very fast, simple, non-cryptographic 32/64 hash function  

The algorithm belongs to the family of LCG (linear congruential generator) like the hash function of java, so it is very simple and fast, but unlike others, zedmee has a much higher quality as it uses some tricks to eliminate the defects of the LCG functions.  
It uses a preloaded table at initialization that contains 256 random values (one for each byte) generated with the algorithm lfsr113 (for 32 bit) or lfsr258 (for 64 bit).  

The use of the random table allows to eliminate the main defect of the LCG functions: their distribution is not perfectly uniform, but follows some patterns that are highlighted through the representation on a two-dimensional map.  

Through the selection of the particular seeds, numerical recipes and other small tricks, zedmee is able to achieve the quality of the best hash functions, maintaining the simplicity and the great speed of the LCGs.  

## Uniform and chaotic distribution of hash values (dispersion)
Zedmee has an absolutely uniform, chaotic distribution of hash values independent of the number, length and type of input values.  
Even minimal differences (1 bit) in the input values produce very different hash values.  

![Alt Text](https://raw.githubusercontent.com/matteo65/ZedmeeHash/main/Resource/zmh_distributions.png)



#### 32-bit hash functions: number of collisions for strings (ASCII 1 byte per char)

Data input                                                  |#Hashes   | Zedmee   | Murmur3 |    XX   |  Rabin
------------------------------------------------------------|----------|----------|---------|---------|---------
Numbers as strings from "0" to "999999999"                  |1,000,000,000| 107,892,934|107,822,463|110,287,893|365,950,432
File Resource words_en.txt                                  | 65,503    |         1|        0|        0|       14
File Resource words_es.txt                                  | 74,571    |         0|        2|        0|       38
File Resource words_it.txt                                  |117,558    |         0|        0|        2|       28
File Resource words_latin.txt                               | 80,007    |         0|        1|        1|       34
File Resource words_en_es_it_latin.txt                      |315,198    |         2|        9|        9|      271
File Resource words_and_numbers.txt                         |429,187    |         5|       20|       19|      251
File Resource first_million_primes.txt                      |1,000,000  |       115|      118|       85|        0
File Resource random_64bit_signed_numbers.txt               |1,000,000  |       110|      110|      143|      122

#### 32-bit hash functions: number of collisions for data input from [19-48] bytes

Data input                                                                             | #Hashes   |  Zedmee   | Murmur3  |     XX   | Rabin
---------------------------------------------------------------------------------------|-----------|-----------|----------|----------|----------
Number as strings from "1234567890123456789" to "1234567890223456789"                  |100,000,000| 1,153,982 | 1,155,789|   808,693|         0      
Strings from "abcdefg1234567890123456789hijklmn" to "abcdefg1234567890223456789hijklmn"|100,000,000| 1,159,126 | 1,152,600| 1,037,151|         0  
Binary 24 bytes [b,b,b,b,b,b], b from 00000000 to 05F5E0FF                             |100,000,000| 1,155,437 | 1,154,653| 1,411,483|         0
Binary 24 bytes [b,b\*3,b\*5,b\*7,b\*11,b\*13], b from 00000000 to 05F5E0FF            |100,000,000| 1,156,949 | 1,154,542| 1,160,003| 1,150,862
Strings 48 length "ssssss", s from "00000000" to "05F5E0FF"                            |100,000,000| 1,154,099 | 1,156,254| 1,155,854|22,595,936


## Vulnerability
Zedmee, like most non-cryptographic functions, is non-secure because it is not specifically designed to be difficult to reverse by an adversary, making it unsuitable for cryptographic purposes. Its use is instead recommended in all other contexts where hash functions are used.  
However, since it uses 4 seeds (32 bits) for 32-bit version (once in the initialization or instantiation) or 5 seeds (64 bit) for 64-bit version, its security is given by the secrecy of these seeds, the combination of which is 128/360 bits which make zedmee certainly less vulnerable than all the other non-cryptographic functions.   

## Portability
It is simple, straightforward and can be easily written in virtually any programming language.  
Currently C and Java versions are for Big Endian architecture but mirror functions for Little Endian can be easily written.    
