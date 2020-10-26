package com.example.topmovies.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.topmovies.model.DetailModel;
import com.example.topmovies.service.ApiClient;
import com.example.topmovies.service.ApiService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailViewModel extends ViewModel {

    /**
     * ApiService instance.
     */
    private ApiService mApiService = ApiClient.getRetrofitInstance().create(ApiService.class);
    /**
     * Composite Disposable instance.
     */
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    /**
     * MutableLiveData for detail data.
     */
    private MutableLiveData<DetailModel> mDetailData;


    /**
     * Set Details Data
     */
    public void setDetailData(int id, String api, String language) {
        Disposable disposable =
                mApiService.getDetailMovies(id, api, language)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(detailModel -> mDetailData.setValue(detailModel),
                                Throwable::printStackTrace);
        mCompositeDisposable.add(disposable);
    }

    /**
     * Get Details Data
     */
    public LiveData<DetailModel> getDataDetail() {
        if (mDetailData == null) {
            mDetailData = new MutableLiveData<>();
        }
        return mDetailData;
    }

    /**
     * Reset CompositeDisposable.
     */
    public void reset() {
        mCompositeDisposable.dispose();
        mCompositeDisposable = null;
    }
}
