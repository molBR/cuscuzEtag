package cuscuz.internals

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import javax.servlet.http.HttpServletRequest

class CusCuzExternals(private val hashType: HashType) {
    companion object {
        private fun generateResponse(etagCurrentValue: String, etagRequestValue: String?, objectInput: Any): ResponseEntity<Any> {
            val headers = HttpHeaders().apply {
                set("ETAG", etagCurrentValue)
            }
            return if (etagRequestValue.isNullOrEmpty() || etagRequestValue != etagCurrentValue) {
                ResponseEntity.ok().headers(headers).body(objectInput)
            } else {
                ResponseEntity.status(HttpStatus.NOT_MODIFIED).headers(headers).body(null)
            }
        }

        private fun extractEtags(objectInput: Any, request: HttpServletRequest, hashFunc: () -> String): ResponseEntity<Any> {
            val currentEtag = hashFunc()
            val requestEtag = request.getHeader("ETAG")

            return generateResponse(currentEtag, requestEtag, objectInput)
        }

        fun responseWithEtagStatic(objectInput: Any, hashType: HashType, request: HttpServletRequest): ResponseEntity<Any> {
            val hashFunc: () -> String = { HashingAlgos.hashStatic(objectInput.hashCode().toString(), hashType) }
            return extractEtags(objectInput, request, hashFunc)
        }
    }

    private val hashingAlgos = HashingAlgos(hashType)

    fun responseWithEtagDynamic(objectInput: Any, request: HttpServletRequest): ResponseEntity<Any> {
        val hashFunc: () -> String = { hashingAlgos.hashDynamic(objectInput.hashCode().toString()) }
        return extractEtags(objectInput, request, hashFunc)
    }
}
