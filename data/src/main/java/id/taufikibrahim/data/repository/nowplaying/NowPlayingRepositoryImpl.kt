package id.taufikibrahim.data.repository.nowplaying

import id.taufikibrahim.data.remote.MovieApiService
import id.taufikibrahim.domain.nowplaying.NowPlayingRepository
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NowPlayingRepositoryImpl @Inject constructor(private val movieApiService: MovieApiService) :
    NowPlayingRepository {

    override fun getNowPlaying(): Flow<ResultState<List<Movie>>> = flow {
        try {
            val response = movieApiService.getNowPlaying()
            if (response.results.isNotEmpty()) {
                emit(ResultState.Success(response.results))
            } else {
                emit(ResultState.Empty)
            }
        } catch (t: Throwable) {
            emit(ResultState.Error(t))
        }
    }.flowOn(Dispatchers.IO)

}