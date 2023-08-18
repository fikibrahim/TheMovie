package id.taufikibrahim.domain.nowplaying

import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NowPlayingUseCaseImpl @Inject constructor(private val repository: NowPlayingRepository) : NowPlayingUseCase {
    override fun getNowPlaying(): Flow<ResultState<List<Movie>>> = repository.getNowPlaying()
}