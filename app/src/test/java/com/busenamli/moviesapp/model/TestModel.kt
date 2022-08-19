package com.busenamli.moviesapp.model

import com.busenamli.moviesapp.data.model.*

object TestModel {

    val castModel = listOf(
        Cast(1, 0, "as", "name", "oname", 1, "charecter", "asa", 2, "adsad"),
        Cast(2, 0, "as", "name", "oname", 1, "charecter", "asa", 2, "adsad")
    )

    val crewModel = listOf(Crew(1, 0, "as", "name", "oname", "asa", "charecter", "asa"))


    val creditModel = Credit(
        1, castModel, crewModel
    )

    val movieDetailModel = MovieDetail(
        1,
        false,
        "ol",
        "title",
        "otitle",
        "overview",
        "rd",
        "sdlkadsl",
        listOf(Genre(1, "genre")),
        120,
        listOf(Language("tr", "adsa", "name")),
        "status",
        "tag",
        listOf(Country("ads", "tr")),
        listOf(Company(1, "sdas", "name", "originCountry")),
        8.2,
        8.3
    )

    val genreListModel = listOf(
        Genre(1, "genre"),
        Genre(2, "genre2"),
        Genre(3, "genre3")
    )

    val movieModel = Movie(
        1,
        false,
        "ol",
        "title",
        "otitle",
        "overview",
        "rd",
        8.4,
        "sfasfd"
    )

    val movieListModel = listOf(
        Movie(
            1,
            false,
            "ol",
            "title",
            "otitle",
            "overview",
            "rd",
            8.4,
            "sfasfd"
        ),
        Movie(
            2,
            false,
            "ol",
            "title",
            "otitle",
            "overview",
            "rd",
            8.1,
            "sfasfd"
        ),
        Movie(
            3,
            false,
            "ol",
            "title",
            "otitle",
            "overview",
            "rd",
            7.2,
            "sfasfd"
        )
    )

    val error = "Sayfa yüklenirken hata oluştu!"
}