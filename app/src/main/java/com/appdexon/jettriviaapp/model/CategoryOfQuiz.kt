package com.appdexon.jettriviaapp.model


enum class CategoryOfQuiz(val nameString: String) {

    ANIMALS("animals"),
    BRAINTEASERS("brain-teasers"),
    CELEBRITIES("celebrities"),
    ENTERTAINMENT("entertainment"),
    FORKIDS("for-kids"),
    GENERAL("general"),
    GEOGRAPHY("geography"),
    HISTORY("history"),
    HOBBIES("hobbies"),
    HUMANITIES("humanities"),
    LITERATURE("literature"),
    MOVIES("movies"),
    MUSIC("music"),
    NEWEST("newest"),
    PEOPLE("people"),
    RATED("rated"),
    RELIGIONFAITH("religion-faith"),
    SCIENCETECHNOLOGY("science-technology"),
    SPORTS("sports"),
    TELEVISION("television"),
    VIDEOGAMES("video-games"),
    WORLD("world");

    companion object {
        fun getEnumFromString(passedString: String?): CategoryOfQuiz? {
            return when(passedString) {
                ANIMALS.nameString -> ANIMALS
                BRAINTEASERS.nameString -> BRAINTEASERS
                CELEBRITIES.nameString -> CELEBRITIES
                ENTERTAINMENT.nameString -> ENTERTAINMENT
                FORKIDS.nameString -> FORKIDS
                GENERAL.nameString -> GENERAL
                GEOGRAPHY.nameString -> GEOGRAPHY
                HISTORY.nameString -> HISTORY
                HOBBIES.nameString -> HOBBIES
                HUMANITIES.nameString -> HUMANITIES
                LITERATURE.nameString -> LITERATURE
                MOVIES.nameString -> MOVIES
                MUSIC.nameString -> MUSIC
                NEWEST.nameString -> NEWEST
                PEOPLE.nameString -> PEOPLE
                RATED.nameString -> RATED
                RELIGIONFAITH.nameString -> RELIGIONFAITH
                SCIENCETECHNOLOGY.nameString -> SCIENCETECHNOLOGY
                SPORTS.nameString -> SPORTS
                TELEVISION.nameString -> TELEVISION
                VIDEOGAMES.nameString -> VIDEOGAMES
                WORLD.nameString -> WORLD
                else -> null
            }
        }
    }
}