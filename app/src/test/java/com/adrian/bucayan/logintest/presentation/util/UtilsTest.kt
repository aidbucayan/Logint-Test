package com.adrian.bucayan.logintest.presentation.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UtilsTest {

    @Test
    fun `invalid email returns false`() {
        val result = Utils.isEmailValid("adrianbucayan@.com")
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun `valid email returns true`() {
        val result = Utils.isEmailValid("adrianbucayan@gmail.com")
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `valid username returns true`() {
        val result = Utils.isUserNameValid("aid")
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `invalid username returns false`() {
        val result = Utils.isUserNameValid("ai")
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun `valid password returns true`() {
        val result = Utils.isPasswordValid("Gg123456")
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `invalid password no uppercase returns false`() {
        val result = Utils.isPasswordValid("gg123456")
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun `invalid password no lowercase returns false`() {
        val result = Utils.isPasswordValid("GG123456")
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun `invalid password no numeric returns false`() {
        val result = Utils.isPasswordValid("Ggssssss")
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun `invalid password minimum length not met returns false`() {
        val result = Utils.isPasswordValid("Gg12345")
        assertThat(result).isEqualTo(false)
    }

}