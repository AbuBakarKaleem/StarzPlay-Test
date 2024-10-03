package com.starzplay.entertainment.models

sealed class UIState {
    object InitialState : UIState()
    object LoadingState : UIState()
    object ContentState : UIState()
    class ErrorState(val message: String) : UIState()
}
