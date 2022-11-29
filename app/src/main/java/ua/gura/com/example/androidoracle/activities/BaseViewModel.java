package ua.gura.com.example.androidoracle.activities;

import androidx.lifecycle.ViewModel;

import ua.gura.com.example.androidoracle.activities.model.DateNagerService;

public class BaseViewModel extends ViewModel {
    private DateNagerService dateNagerService;

    public BaseViewModel(DateNagerService dateNagerService) {
        this.dateNagerService = dateNagerService;
    }

    protected DateNagerService getDateNagerService() {
        return dateNagerService;
    }
}
