package co.api.docs

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _apis = MutableStateFlow<List<Api>>(emptyList())

    private val _list = MutableStateFlow<List<Doc>>(emptyList())
    val list: StateFlow<List<Doc>> = _list

    fun onDocReceived(response: List<Api>) {
        _apis.value = response
        _list.value = response.map { it.toDoc() }
    }

    fun onItemClicked(doc: Doc) {
        if (doc !is Doc.Api) return
        val api = _apis.value.first { it.name == doc.name }

        if (_list.value.any { it is Doc.Endpoint && api.endpoints.any { e -> e.path == it.path } }) {
            _list.value = _list.value.filterIsInstance<Doc.Api>()
            return
        }

        val docs = mutableListOf<Doc>()
        _apis.value.forEach {
            if (it.name == doc.name) {
                docs.add(it.toDoc())
                it.endpoints.forEach { e ->
                    docs.add(e.toDoc())
                }
            } else {
                docs.add(it.toDoc())
            }
        }

        _list.value = docs

    }

}

