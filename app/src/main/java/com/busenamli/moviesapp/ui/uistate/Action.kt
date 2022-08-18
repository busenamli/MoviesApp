package com.busenamli.moviesapp.ui.uistate

sealed class Action {
    object FromGenreList : Action()
    object FromMovieList : Action()
}