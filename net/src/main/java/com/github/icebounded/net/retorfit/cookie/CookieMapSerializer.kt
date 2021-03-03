package com.github.icebounded.net.retorfit.cookie

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import okhttp3.Cookie

/**
 * Created by Andy
 * on 2021/3/3
 */
class CookieMapSerializer : KSerializer<Cookie> {

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
            val name = decodeStringElement(descriptor, 0)
            val value = decodeStringElement(descriptor, 1)
            val expiresAt: Long = decodeLongElement(descriptor, 2)
            val domain = decodeStringElement(descriptor, 3)
            val path = decodeStringElement(descriptor, 4)
            val secure: Boolean = decodeBooleanElement(descriptor, 5)
            val httpOnly: Boolean = decodeBooleanElement(descriptor, 6)
            val hostOnly: Boolean = decodeBooleanElement(descriptor, 7)
            val persistent: Boolean = decodeBooleanElement(descriptor, 8)

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