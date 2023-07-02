import io.mockk.every
import io.mockk.mockk


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import cuscuz.internals.CusCuzExternals
import cuscuz.internals.HashType
import cuscuz.internals.HashingAlgos
import javax.servlet.http.HttpServletRequest

class CusCuzExternalsTests {
    @Test
    fun `responseWithEtagStatic returns expected response`() {
        // Arrange
        val objectInput = "test"
        val hashType = HashType.SHA256
        val request = mockk<HttpServletRequest>()
        val expectedEtag = "5866afb28ed232988fb9485c7306da8cff02c1c48f1a196791558e48b8b58c38"
        every { request.getHeader("ETAG")}.returns(expectedEtag)

        // Act
        val response = CusCuzExternals.responseWithEtagStatic(objectInput, hashType, request)

        // Assert
       val expectedResponse = ResponseEntity.status(HttpStatus.NOT_MODIFIED).header("ETAG", expectedEtag).body(null)
        assertEquals(expectedResponse, response)
    }

    @Test
    fun `responseWithEtagDynamic returns expected response`() {
        // Arrange
        val objectInput = "test"
        val hashType = HashType.SHA256
        val request = mockk<HttpServletRequest>()
        val expectedEtag = "5866afb28ed232988fb9485c7306da8cff02c1c48f1a196791558e48b8b58c38"
        every { request.getHeader("ETAG")}.returns(expectedEtag)

        // Act
        val response = CusCuzExternals(hashType).responseWithEtagDynamic(objectInput, request)

        // Assert
        val hashingAlgos = HashingAlgos(hashType)
        val expectedResponse = ResponseEntity.status(HttpStatus.NOT_MODIFIED).header("ETAG", expectedEtag).body(null)
        assertEquals(expectedResponse, response)
    }


}