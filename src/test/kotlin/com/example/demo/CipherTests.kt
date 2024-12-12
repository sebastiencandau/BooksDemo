package com.example.demo

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.property.Arb
import io.kotest.property.arbitrary.char
import io.kotest.property.arbitrary.int
import io.kotest.property.forAll

class CipherTests : StringSpec({

    "cipher should return 'C' when input is 'A' and key is 2" {
        cipher('A', 2) shouldBe 'C'
    }

    "cipher should return 'F' when input is 'A' and key is 5" {
        cipher('A', 5) shouldBe 'F'
    }

    "cipher should return 'A' when input is 'Z' and key is 1" {
        cipher('Z', 1) shouldBe 'A'
    }

    "cipher should cycle back correctly when key is greater than 26" {
        cipher('A', 27) shouldBe 'B' // 27 % 26 = 1
    }

    "cipher should throw an exception if the key is negative" {
        shouldThrow<IllegalArgumentException> {
            cipher('A', -1)
        }
    }

    "cipher should throw an exception if input is not an uppercase letter" {
        shouldThrow<IllegalArgumentException> {
            cipher('a', 2) // Lowercase letter
        }
        shouldThrow<IllegalArgumentException> {
            cipher('1', 2) // Digit
        }
        shouldThrow<IllegalArgumentException> {
            cipher('!', 2) // Special character
        }
    }

 /*        PROPERTY-BASED TESTS */

    "cipher should return the same character for key 0" {
        forAll(Arb.char('A'..'Z')) { char ->
            cipher(char, 0) == char
        }
    }

    "cipher should cycle back to the same character for multiples of 26" {
        forAll(Arb.char('A'..'Z'), Arb.int(min = 1, max = 10)) { char, multiplier ->
            val key = multiplier * 26
            cipher(char, key) == char
        }
    }

    "cipher with a key and its negative counterpart should return the original character" {
        forAll(Arb.char('A'..'Z'), Arb.int(min = 0, max = 25)) { char, key ->
            cipher(cipher(char, key), 26 - key) == char
        }
    }

    "cipher output should always be within 'A' to 'Z'" {
        forAll(Arb.char('A'..'Z'), Arb.int(min = 0, max = 100)) { char, key ->
            val result = cipher(char, key)
            result in 'A'..'Z'
        }
    }
})
