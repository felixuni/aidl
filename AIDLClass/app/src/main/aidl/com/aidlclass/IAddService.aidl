// IAddService.aidl
package com.aidlclass;

// Declare any non-default types here with import statements

interface IAddService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

            int add(in int PrimerValor,in int SegundoValor);
}