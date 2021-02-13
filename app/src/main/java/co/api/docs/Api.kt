package co.api.docs

data class Api(val name: String, val endpoints: List<Endpoint>) {

    fun toDoc(): Doc.Api {
        return Doc.Api(name)
    }

}

data class Endpoint(val method: String, val path: String) {

    fun toDoc(): Doc {
        return Doc.Endpoint(method, path)
    }

}
