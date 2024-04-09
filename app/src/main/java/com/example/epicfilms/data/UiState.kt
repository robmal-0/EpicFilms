package com.example.epicfilms.data

data class UiState(
    val popular: ArrayList<Movie> = arrayListOf(
        Movie(
            id = 550,
            popularity = 85.16f,
            vote_average = 8.4f,
            title = "Fight Club",
            backdrop_path = "/hZkgoQYus5vegHoetLkCJzb17zJ.jpg",
            imdb_id = "tt0137523",
            homepage = "http://www.foxmovies.com/movies/fight-club",
            overview = "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \\\"fight clubs\\\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
            poster_path = "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
            genres = arrayListOf(
                Genre(
                    id = 18,
                    name = "Drama"
                )
            )
        ),
        Movie(
            id = 455,
            popularity = 14.78f,
            vote_average = 6.3f,
            title = "Bend It Like Beckham",
            backdrop_path = "/zEjBQIrUEr5rt24HeLad4I4PnR1.jpg",
            imdb_id = "tt0286499",
            homepage = "",
            overview = "Jess Bhamra, the daughter of a strict Indian couple in London, is not permitted to play organized soccer, even though she is 18. When Jess is playing for fun one day, her impressive skills are seen by Jules Paxton, who then convinces Jess to play for her semi-pro team. Jess uses elaborate excuses to hide her matches from her family while also dealing with her romantic feelings for her coach, Joe.",
            poster_path = "/2dzSBmFWWqt8NbnubJKIWU21Y86.jpg",
            genres = arrayListOf(
                Genre(
                    id = 35,
                    name = "Comedy"
                ),
                Genre(
                    id = 18,
                    name = "Drama"
                ),
                Genre(
                    id = 10749,
                    name = "Romance"
                )
            )
        )
    ),
    val topRated: ArrayList<Movie> = arrayListOf(
        Movie(
            id = 550,
            popularity = 85.16f,
            vote_average = 8.4f,
            title = "Fight Club",
            backdrop_path = "/hZkgoQYus5vegHoetLkCJzb17zJ.jpg",
            imdb_id = "tt0137523",
            homepage = "http://www.foxmovies.com/movies/fight-club",
            overview = "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \\\"fight clubs\\\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
            poster_path = "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
            genres = arrayListOf(
                Genre(
                    id = 18,
                    name = "Drama"
                )
            )
        ),
        Movie(
            id = 455,
            popularity = 14.78f,
            vote_average = 6.3f,
            title = "Bend It Like Beckham",
            backdrop_path = "/zEjBQIrUEr5rt24HeLad4I4PnR1.jpg",
            imdb_id = "tt0286499",
            homepage = "",
            overview = "Jess Bhamra, the daughter of a strict Indian couple in London, is not permitted to play organized soccer, even though she is 18. When Jess is playing for fun one day, her impressive skills are seen by Jules Paxton, who then convinces Jess to play for her semi-pro team. Jess uses elaborate excuses to hide her matches from her family while also dealing with her romantic feelings for her coach, Joe.",
            poster_path = "/2dzSBmFWWqt8NbnubJKIWU21Y86.jpg",
            genres = arrayListOf(
                Genre(
                    id = 35,
                    name = "Comedy"
                ),
                Genre(
                    id = 18,
                    name = "Drama"
                ),
                Genre(
                    id = 10749,
                    name = "Romance"
                )
            )
        )
    )
)
