package com.clementcoutarel.musicapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _currentScreen: MutableState<Screen> = mutableStateOf(Screen.DrawerScreen.Account)
    val currentScreen: MutableState<Screen> = _currentScreen

    fun setCurrentScreen(screen: Screen){
        _currentScreen.value = screen
    }

}