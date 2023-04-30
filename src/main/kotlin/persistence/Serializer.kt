/**
 * Made by Joel Amoakoh
 * Student 20096482
 */
package persistence

interface Serializer {
    @Throws(Exception::class)
    fun write(obj: Any?)

    @Throws(Exception::class)
    fun read(): Any?
}