package cuscuz.internals

import java.math.BigInteger
import java.security.MessageDigest


enum class HashType(val hashingString: String){
    SHA1("SHA-1"),
    SHA256("SHA-256"),
    SHA384("SHA-384")
}


internal class HashingAlgos (val hashType: HashType) {

    private val instanceDigest: MessageDigest = MessageDigest.getInstance(hashType.hashingString)

    companion object {
        private fun hashing(input : String, md : MessageDigest): String {
            val messageDigest = md.digest(input.toByteArray())
            val no = BigInteger(1, messageDigest)
            return no.toString(16)
        }

        fun hashStatic(input: String, hashType: HashType) : String{
            val md = MessageDigest.getInstance(hashType.hashingString)
            return hashing(input, md)
        }
    }
    fun hashDynamic(input: String): String{
        return hashing(input, instanceDigest)
    }
}