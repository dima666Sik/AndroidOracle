package ua.gura.com.example.androidoracle.contract;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ua.gura.com.example.androidoracle.model.User;

public interface AppContract {
    /**
     * Launch options screen
     * @param target fragment that launches options screen
     * @param user data about the User to be displayed
     */
    void toSettingScreen(Fragment target,
                         @Nullable User user);
    /**
     * Launch results screen
     * @param target fragment that launches results screen
     * @param user data used for calculating variant
     */
    void toResultsScreen(Fragment target, User user);
    /**
     * Exit from the current screen
     */
    void cancel();
    /**
     * Publish results to the target screen
     */
    <T> void publish(T data);
    /**
     * Listen for results from other screens
     */
    <T> void registerListener(Fragment fragment, Class<T> clazz,
                              ResponseListener<T> listener);
    /**
     * Stop listening for results from other screens
     */
    void unregisterListeners(Fragment fragment);

}
