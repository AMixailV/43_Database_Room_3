package ru.mixail_akulov.a43_database_room_3.screens.main.tabs.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.mixail_akulov.a43_database_room_3.R
import ru.mixail_akulov.a43_database_room_3.model.StorageException
import ru.mixail_akulov.a43_database_room_3.model.boxes.BoxesRepository
import ru.mixail_akulov.a43_database_room_3.model.boxes.entities.Box
import ru.mixail_akulov.a43_database_room_3.model.boxes.entities.BoxAndSettings
import ru.mixail_akulov.a43_database_room_3.utils.MutableLiveEvent
import ru.mixail_akulov.a43_database_room_3.utils.publishEvent
import ru.mixail_akulov.a43_database_room_3.utils.share

class SettingsViewModel(
    private val boxesRepository: BoxesRepository
) : ViewModel(), SettingsAdapter.Listener {

    private val _boxSettings = MutableLiveData<List<BoxAndSettings>>()
    val boxSettings = _boxSettings.share()

    private val _showErrorMessageEvent = MutableLiveEvent<Int>()
    val showErrorMessageEvent =_showErrorMessageEvent.share()

    init {
        viewModelScope.launch {
            boxesRepository.getBoxesAndSettings().collect {
                _boxSettings.value = it
            }
        }
    }

    override fun enableBox(box: Box) {
        viewModelScope.launch {
            try {
                boxesRepository.activateBox(box)
            } catch (e: StorageException) {
                showStorageErrorMessage()
            }
        }
    }

    override fun disableBox(box: Box) {
        viewModelScope.launch {
            try {
                boxesRepository.deactivateBox(box)
            } catch (e: StorageException) {
                showStorageErrorMessage()
            }
        }
    }

    private fun showStorageErrorMessage() {
        _showErrorMessageEvent.publishEvent(R.string.storage_error)
    }
}