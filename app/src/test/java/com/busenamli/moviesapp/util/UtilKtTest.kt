package com.busenamli.moviesapp.util

import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class UtilKtTest {

    @Before
    fun setup(){

    }

    @After
    fun teardown(){

    }

    @Test
    fun doubleFormat() {
        val actual = doubleFormat(8.2)
        val expected = "8,2"
        assertEquals(expected,actual)
    }
}