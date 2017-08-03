/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.java.zhassan.io.fastscanner;

import java.io.IOException;
import java.io.InputStream;

/**
 * The alternative buffered fast scanner for InputStream in Java. It is based on
 * pure primitives and outperforms java.io.BufferedReader up to 20 times.
 *
 * @author Zhassan
 */
public class FastScanner {

    static final int DEFAULT_BUFF = 1024, EOF = -1, INT_MIN = 48, INT_MAX = 57, NOT_SET = -1, POS = 1, DOT = 46, SPACE = 32, NL = 10, CR = 13;
    static final byte NEG = 45;
    /**
     * Stores digits for corresponding byte values
     */
    static final int[] digits = new int[58];

    /**
     * Assigning digits for corresponding byte values
     */
    static {
        int value = 0;
        for (int i = 48; i < 58; i++) {
            digits[i] = value++;
        }
    }

    InputStream stream;
    /**
     * Current buffer of bytes in memory
     */
    byte[] buff;
    /**
     * Pointer that points to the byte position in bytes buffer array
     */
    int buffPtr;
    /**
     * Shows how many bytes are read into buffer;
     */
    int numBytes;

    /**
     * Constructor of FastScanner with default bytes buffer size
     *
     * @param stream - input stream to scan
     */
    public FastScanner(InputStream stream) {
        this.stream = stream;
        this.buff = new byte[DEFAULT_BUFF];
        this.buffPtr = NOT_SET;
    }

    /**
     * Constructor of FastScanner with custom bytes buffer size
     *
     * @param stream - input stream to scan
     * @param bufferSize - custom buffer size to read bytes into
     */
    public FastScanner(InputStream stream, int bufferSize) {
        this.stream = stream;
        this.buff = new byte[bufferSize];
        this.buffPtr = NOT_SET;
    }

    /**
     * Method for obtaining next available 32 bit integer from the input. Not
     * that it just skips all the non numeric characters around numeric slice
     * and returns the numeric slice.
     *
     * @return - 32 bit integer value if one exist in input.
     * @throws IOException if no more digits are found
     */
    public int nextInt() throws IOException {
        int val = 0;
        int sign = readNonNumerics();
        while (isDigit(buff[buffPtr]) &&  numBytes != EOF) {
            val = (val << 3) + (val << 1) + digits[buff[buffPtr]];
            buffPtr++;
            if (buffPtr == numBytes) {
                updateBuff();
            }
        }
        return val * sign;
    }

    /**
     * Method for obtaining next available 64 bit integer from the input. Note
     * that it just skips all the non numeric characters around numeric slice
     * and returns the numeric slice.
     *
     * @return - 64 bit integer value if one exist in input.
     * @throws IOException if no more digits are found
     */
    public long nextLong() throws IOException {
        long val = 0L;
        int sign = readNonNumerics();
        while (isDigit(buff[buffPtr]) && numBytes != EOF) {
            val = (val << 3) + (val << 1) + digits[buff[buffPtr]];
            buffPtr++;
            if (buffPtr == numBytes) {
                updateBuff();
            }
        }
        return val * sign;
    }


    /**
     * Method for reading next available 64 bit floating point number(double).
     * It is just a simple implementation with parse double operation. Further enhancements will be thought out.    
     */
    public double nextDouble() throws IOException{
        double val = 0d;
        int sign = readNonNumerics();
        String doubleStr = next();
        val = Double.parseDouble(doubleStr);
        return val*sign;
    }
    /**
     * Reads next string slice
     *
     * @return String the next available string without space characters
     * @throws IOException if some exception occurs while input output
     */
    public String next() throws IOException {
        readAllSpaces();
        StringBuilder res = new StringBuilder();
        while (numBytes != EOF && buff[buffPtr] != SPACE && buff[buffPtr] != NL && buff[buffPtr] != CR) {
            res.append((char) buff[buffPtr]);
            buffPtr++;
            if (buffPtr == buff.length) {
                updateBuff();
            }
        }
        return res.toString();
    }

    /**
     * Reads all non numeric symbols and assigns the buffer's pointer to the
     * first available numeric character byte
     *
     * @return integer sign denoting the sign of the integer to be red next
     * @throws IOException if no more numeric character is found
     */
    private int readNonNumerics() throws IOException {
        if (buffPtr == NOT_SET || buffPtr == buff.length) {
            updateBuff();
        }
        if (numBytes == EOF) {
            throw new IOException("End of stream reached");
        }
        int signByte = NOT_SET;
        while (!isDigit(buff[buffPtr])) {
            signByte = buff[buffPtr];
            buffPtr++;
            if (buffPtr >= buff.length) {
                updateBuff();
            }
            if (numBytes == EOF) {
                throw new IOException("End of stream reached");
            }
        }
        if (signByte == NEG) {
            return -POS;
        }
        return POS;
    }

    private void readAllSpaces() throws IOException {
        if (buffPtr == NOT_SET || buffPtr >= numBytes) {
            updateBuff();
        }
        if (numBytes == EOF) {
            throw new IOException("End of stream reached");
        }
        while (buff[buffPtr] == SPACE || buff[buffPtr] == NL || buff[buffPtr] == CR) {
            buffPtr++;
            if (buffPtr >= numBytes) {
                updateBuff();
                
            }
            if (numBytes == EOF) {
                throw new IOException("End of stream reached");
            }
        }
    }

    /**
     * Closes the input stream passed as parameter to a constructor. We
     * recommend to avoid calling this method explicitly if not necessary!
     *
     * @throws IOException if some exception occurs while closing the stream
     */
    public void close() throws IOException {
        stream.close();
    }

    /**
     * Checks whether byte is for numeric character
     *
     * @param b - byte number of character
     * @return true if the input byte number is for numeric character
     */
    private boolean isDigit(int b) {
        return b >= INT_MIN && b <= INT_MAX;
    }

    /**
     * Loads next buffer of bytes and resets the buffer pointer
     *
     * @throws IOException if some exception occurs while reading bytes into the
     * buffer array.
     */
    private void updateBuff() throws IOException {
        buffPtr = 0;
        numBytes = stream.read(buff);
    }
    
    public void reset() throws IOException{
        this.stream.reset();
    }
}
