package com.example.filereview.ui.myself;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.filereview.R;

public class MyselfFragment extends Fragment {
    private MyselfViewModel myselfViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myselfViewModel =
                ViewModelProviders.of(this).get(MyselfViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myself, container, false);

        return root;
    }
}
