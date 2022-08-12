package com.busenamli.moviesapp.ui.uistate

sealed class Action {
    data class FromGenreList(val isFromGenreList: Boolean) : Action()
    data class FromMovieList(val isFromMovieList: Boolean) : Action()
}