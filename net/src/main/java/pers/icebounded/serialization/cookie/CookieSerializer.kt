package pers.icebounded.serialization.cookie

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import okhttp3.Cookie

/**
 * Created by Andy
 * on 2021/3/2
 */
class CookieSerializer : KSerializer<Cookie> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Cookie") {
        element<String>("name")
        element<String>("value")
        element<Long>("expiresAt")
        element<String>("domain")
        element<String>("path")
        element<Boolean>("secure")
        element<Boolean>("httpOnly")
        element<Boolean>("hostOnly")
        element<Boolean>("persistent")
    }

    override fun deserialize(decoder: Decoder): Cookie {
        require(decoder is JsonDecoder) // this class can be decoded only by Json
        return decoder.decodeStructure(descriptor) {

            lateinit var name: String
            lateinit var value: String
            var expiresAt: Long = 0
            lateinit var domain: String
            lateinit var path: String
            var secure = false
            var httpOnly = false
            var hostOnly = false
            var persistent = false

            if (decodeSequentially()) { // sequential decoding protocol
                name = decodeStringElement(descriptor, 0)
                value = decodeStringElement(descriptor, 1)
                expiresAt = decodeLongElement(descriptor, 2)
                domain = decodeStringElement(descriptor, 3)
                path = decodeStringElement(descriptor, 4)
                secure = decodeBooleanElement(descriptor, 5)
                httpOnly = decodeBooleanElement(descriptor, 6)
                hostOnly = decodeBooleanElement(descriptor, 7)
                persistent = decodeBooleanElement(descriptor, 8)
            } else while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> name = decodeStringElement(descriptor, 0)
                    1 -> value = decodeStringElement(descriptor, 1)
                    2 -> expiresAt = decodeLongElement(descriptor, 2)
                    3 -> domain = decodeStringElement(descriptor, 3)
                    4 -> path = decodeStringElement(descriptor, 4)
                    5 -> secure = decodeBooleanElement(descriptor, 5)
                    6 -> httpOnly = decodeBooleanElement(descriptor, 6)
                    7 -> hostOnly = decodeBooleanElement(descriptor, 7)
                    8 -> persistent = decodeBooleanElement(descriptor, 8)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }

            Cookie.Builder().apply {
                name(name)
                value(value)
                expiresAt(expiresAt)
                if (hostOnly) hostOnlyDomain(domain) else domain(domain)
                path(path)
                if (secure) secure()
                if (httpOnly) httpOnly()
            }.build()
        }
    }

    override fun serialize(encoder: Encoder, value: Cookie) {
        // Encoder -> JsonEncoder
        require(encoder is JsonEncoder) // This class can be encoded only by Json
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.name)
            encodeStringElement(descriptor, 1, value.value)
            encodeLongElement(descriptor, 2, value.expiresAt)
            encodeStringElement(descriptor, 3, value.domain)
            encodeStringElement(descriptor, 4, value.path)
            encodeBooleanElement(descriptor, 5, value.secure)
            encodeBooleanElement(descriptor, 6, value.httpOnly)
            encodeBooleanElement(descriptor, 7, value.hostOnly)
            encodeBooleanElement(descriptor, 8, value.persistent)
        }
    }

}