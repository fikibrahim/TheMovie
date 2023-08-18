package id.taufikibrahim.domain.nowplaying

import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.Movie
import kotlinx.coroutines.flow.Flow

interface NowPlayingUseCase {
    fun getNowPlaying(): Flow<ResultState<List<Movie>>>
}