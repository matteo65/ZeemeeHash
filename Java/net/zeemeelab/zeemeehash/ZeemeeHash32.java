package net.zeemeelab.zeemeehash;

/**
 * ZeemeeHash32 
 * Very strong, fast, non-cryptographic 32-bit hash function
 * 
 * Copyright(C) 2021 - Matteo Zapparoli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
public class ZeemeeHash32 extends ZeemeeHash {
	
	public static final int ASCII_ALPHANUM_SEED = 0x189DDD86; 
	public static final int ASCII_LANGUAGE_WORDS_SEED = 0x9D00256A; 
	public static final int ASCII_PRINTABLE_SEED = 0xFDC40804; 
	public static final int ASCII_DIGIT_SEED = 0x2F422C8D; 
	
	public static final int DEFAULT_SEED = ASCII_ALPHANUM_SEED; 

	public ZeemeeHash32(int seed1, int seed2, int seed3, int seed4) {
		super(seed1, seed2, seed3, seed4);
	}
	
	public ZeemeeHash32(int seed) {
		super(seed);
	}
	
	public ZeemeeHash32() {
		super(DEFAULT_SEED);
	}

	/**
	 * Evaluate and return the hash value of [data[pos], data[pos+1] ... data[pos + length - 1]]
	 * @param data
	 * @param pos >= 0 && pos < data.length
	 * @param length >= 1 && pos + length <= data.length
	 * @return hash value
	 */
	public int hash(final byte[] data, int pos, int length) {
		int b1, b2, b3, b4;
		
		switch(length) {
		case 1:
			b1 = b2 = b3 = byteTable0;
			b4 = byteTable[data[pos] & 0xff];
			break;
			
		case 2:
			b1 = b2 = byteTable0;
			b3 = byteTable[data[pos] & 0xff];
			b4 = byteTable[data[pos + 1] & 0xff];
			break;
			
		case 3:
			b1 = byteTable0;
			b2 = byteTable[data[pos] & 0xff];
			b3 = byteTable[data[pos + 1] & 0xff];
			b4 = byteTable[data[pos + 2] & 0xff];
			break;
			
		default:
			b1 = byteTable[data[pos++] & 0xff];
			b2 = byteTable[data[pos++] & 0xff];
			b3 = byteTable[data[pos++] & 0xff];
			b4 = byteTable[data[pos++] & 0xff];
		}

		b1 = byteTable[b1 ^ b4];
		b2 = byteTable[b2 ^ b1];
		b3 = byteTable[b3 ^ b2];
		b4 = byteTable[b4 ^ b3];
		
		int x = b2 ^ b1;
		int y = b4 ^ b3;
		int h = ((b3 ^ x) << 24) | ((b4 ^ x) << 16) | ((y ^ b1) << 8) | (y ^ b2);
		
		if(length > 4) {
			length = pos + length - 4;
    			do {
    				h = h * 134775813 + data[pos++];
    			}
    			while(pos < length);
		}
		return h;
	}
	
	public int hash(final byte[] data, int length) {
		return hash(data, 0, length);
	}
	
	public int hash(final byte[] data) {
		return hash(data, 0, data.length);
	}
	
}
