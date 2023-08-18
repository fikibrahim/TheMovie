package id.taufikibrahim.data.repository.toprated

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.taufikibrahim.domain.BuildConfig
import id.taufikibrahim.domain.popular.PopularRepository
import id.taufikibrahim.domain.toprated.TopRatedRepository
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.Discover
import javax.inject.Inject

class TopRatedPagingSource @Inject constructor(var topRatedRepository: TopRatedRepository) : PagingSource<Int, Discover>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Discover> {
        return try {
            val mutableListMovies = mutableListOf<Discover>()
            val nextPageNumber = params.key ?: BuildConfig.DEFAULT_PAGE_INDEX
            val nextPrevNumber = if (nextPageNumber == BuildConfig.DEFAULT_PAGE_INDEX) null else nextPageNumber - 1
            val response = topRatedRepository.getTopRated(page = nextPageNumber)
            var throwable = Throwable()
            response.collect { list ->
                when(list) {
                    is ResultState.Success -> mutableListMovies.addAll(list.data)
                    is ResultState.Error -> throwable = list.throwable
                    is ResultState.Empty -> Log.e("DataSource", "Empty")
                }
            }

            if (throwable.message != null) {
                return LoadResult.Error(throwable)
            }
            return LoadResult.Page(
                data = mutableListMovies,
                prevKey = nextPrevNumber,
                nextKey = if (mutableListMovies.size > 0) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            Log.e("TAG", "error: $e", )
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Discover>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}