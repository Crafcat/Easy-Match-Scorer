package com.example.easy_match_scorer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import com.example.easy_match_scorer.databinding.ActivityMainBinding;

/*
 * 利用SavedStated方式实现临时存储，防止切换后台时被系统中断导致数据丢失
 * 通过DataBinding和ViewModel实现MVVM
 * */

public class MainActivity extends AppCompatActivity {
    ScorerViewModel scorerViewModel;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        scorerViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this)).get(ScorerViewModel.class);

        binding.setData(scorerViewModel);
        binding.setLifecycleOwner(this);
    }

    /*
     * 程序结束或者终止会调用onPause
     * */
    @Override
    protected void onPause() {
        super.onPause();
        scorerViewModel.save();
    }
}