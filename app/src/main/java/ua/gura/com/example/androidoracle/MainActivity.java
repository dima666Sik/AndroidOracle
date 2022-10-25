package ua.gura.com.example.androidoracle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ua.gura.com.example.androidoracle.contract.AppContract;
import ua.gura.com.example.androidoracle.contract.ResponseListener;
import ua.gura.com.example.androidoracle.fragments.MenuFragment;
import ua.gura.com.example.androidoracle.fragments.QuestionFragment;
import ua.gura.com.example.androidoracle.fragments.SettingFragment;
import ua.gura.com.example.androidoracle.model.User;

public class MainActivity extends AppCompatActivity
        implements AppContract {
    public static final String TAG =
            MainActivity.class.getSimpleName();
    private Map<String, List<ListenerInfo<?>>> listeners =
            new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            launchFragment(null, new MenuFragment());
        }
    }

    @Override
    public void onBackPressed() {
        cancel();
    }

    @Override
    public void toSettingScreen(Fragment target,
                                User user) {
        launchFragment(target,
                SettingFragment.newInstance(user));
    }

    @Override
    public void toResultsScreen(Fragment target,
                                User user) {
        launchFragment(target, QuestionFragment.newInstance(user));
    }

    @Override
    public void cancel() {
        int count = getSupportFragmentManager()
                .getBackStackEntryCount();
        if (count <= 1) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public <T> void publish(T results) {
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment == null) {
            Log.e(TAG, "Can't find the current fragment");
            return;
        }
        Fragment targetFragment =
                currentFragment.getTargetFragment();
        if (targetFragment == null) {
            Log.e(TAG, "Fragment " + currentFragment +
                    " doesn't have a target");
            return;
        }
        String tag = targetFragment.getTag();
        if (tag == null) {
            Log.e(TAG, "Target fragment exists but doesn't have a tag: " + targetFragment);
            return;
        }
        List<ListenerInfo<?>> listeners =
                this.listeners.get(tag);
        if (listeners != null) {
            Iterator<ListenerInfo<?>> it = listeners.iterator();
            while (it.hasNext() &&
                    !it.next().tryPublish(results));
        }
    }
    @Override
    public <T> void registerListener(Fragment fragment,
                                     Class<T> clazz,
                                     ResponseListener<T> listener) {
        if (fragment.getTag() == null) {
            Log.e(TAG, "Fragment '" + fragment +
                    "' doesn't have a tag");
            return;
        }
        List<ListenerInfo<?>> listeners =
                this.listeners.get(fragment.getTag());
        if (listeners == null) {
            listeners = new ArrayList<>();
            this.listeners.put(fragment.getTag(), listeners);
        }
        listeners.add(new ListenerInfo<>(clazz, listener));
    }
    @Override
    public void unregisterListeners(Fragment fragment) {
        if (fragment.getTag() == null) {
            Log.e(TAG, "Fragment '" + fragment +
                    "' doesn't have a tag");
            return;
        }
        this.listeners.remove(fragment.getTag());
    }
    private void launchFragment(@Nullable Fragment target,
                                Fragment fragment) {
        if (target != null) {
            fragment.setTargetFragment(target, 0);
        }
        String tag = UUID.randomUUID().toString();
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, fragment, tag)
                .commit();
    }
    private Fragment getCurrentFragment() {
        return getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainer);
    }
    private static class ListenerInfo<T> {
        Class<T> clazz;
        ResponseListener<T> listener;
        private ListenerInfo(Class<T> clazz,
                             ResponseListener<T> listener) {
            this.clazz = clazz;
            this.listener = listener;
        }
        boolean tryPublish(Object result) {
            if (result.getClass().equals(clazz)) {
                listener.onResults((T) result);
                return true;
            }
            return false;
        }
    }
}