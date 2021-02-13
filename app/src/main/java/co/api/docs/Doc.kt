package co.api.docs

sealed class Doc {

    data class Api(val name: String) : Doc()

    data class Endpoint(val methodType: String, val path: String) : Doc()

}
