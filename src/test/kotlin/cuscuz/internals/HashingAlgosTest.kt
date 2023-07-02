package cuscuz.internals

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class HashingAlgosTest {


    @Test
    fun testHashStatic_SHA256() {
        val input = "OpenAI"
        val expectedHash = "8b7d1a3187ab355dc31bc683aaa71ab5ed217940c12196a9cd5f4ca984babfa4" // The expected SHA-256 hash value for "OpenAI"
        val actualHash = HashingAlgos.hashStatic(input, HashType.SHA256)
        assertEquals(expectedHash, actualHash)
    }


    @Test
    fun testHashDynamic_SHA256() {
        val input = "OpenAI"
        val expectedHash = "8b7d1a3187ab355dc31bc683aaa71ab5ed217940c12196a9cd5f4ca984babfa4" // The expected SHA-256 hash value for "OpenAI"
        val hashingAlgos = HashingAlgos(HashType.SHA256)
        val actualHash = hashingAlgos.hashDynamic(input)
        assertEquals(expectedHash, actualHash)
    }
}