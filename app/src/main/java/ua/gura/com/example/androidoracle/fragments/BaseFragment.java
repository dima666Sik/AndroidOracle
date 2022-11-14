package ua.gura.com.example.androidoracle.fragments;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ua.gura.com.example.androidoracle.contract.AppContract;
import ua.gura.com.example.androidoracle.contract.ResponseListener;

public class BaseFragment extends Fragment {

    private AppContract appContract;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.appContract = (AppContract) context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        this.appContract.unregisterListeners(this);
        this.appContract = null;
    }
    final AppContract getAppContract() {
        return appContract;
    }
    final <T> void registerListener(Class<T> clazz,
                                    ResponseListener<T> listener) {
        getAppContract().registerListener(this, clazz, listener);
    }
}