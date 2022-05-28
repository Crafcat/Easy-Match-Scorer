package com.example.easy_match_scorer;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

public class ScorerViewModel extends AndroidViewModel {
    private int aBack,bBack;
    SavedStateHandle handle;

    public ScorerViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        this.handle = handle;
    }

    /*————————————————————私有模块————————————————————*/
    //加载模块
    private void Load(String KEY){
        Integer defValue = getApplication().getResources().getInteger(R.integer.DefValue);
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        int x = sharedPreferences.getInt(KEY,defValue);
        handle.set(KEY,x);
    }
    //保存模块
    private void Save(String KEY){
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(KEY,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY,getScore(KEY).getValue());
        editor.apply();
    }
    //模块getScore
    private MutableLiveData<Integer> getScore(String KEY){
        if(!handle.contains(KEY)){
            Load(KEY);
        }
        return handle.getLiveData(KEY);
    }
    private void add(int x,String KEY){
        handle.set(KEY,getScore(KEY).getValue() + x);
    }


    /*————————————————————功能区————————————————————*/

    //获取A队得分
    public MutableLiveData<Integer> getAScore(){
        String Key = getApplication().getResources().getString(R.string.KEY_Ateam_NUMBER);
        MutableLiveData<Integer> res = getScore(Key);
        return res;
    }
    //获取B队得分
    public MutableLiveData<Integer> getBScore(){
        String Key = getApplication().getResources().getString(R.string.KEY_Bteam_NUMBER);
        MutableLiveData<Integer> res = getScore(Key);
        return res;
    }

    //A队得分
    public void Aadd(int x){
        String Key = getApplication().getResources().getString(R.string.KEY_Ateam_NUMBER);
        aBack = getAScore().getValue();
        bBack = getBScore().getValue();
        add(x,Key);
    }

    //B队得分
    public void Badd(int x){
        String Key = getApplication().getResources().getString(R.string.KEY_Bteam_NUMBER);
        aBack = getAScore().getValue();
        bBack = getBScore().getValue();
        add(x,Key);
    }

    //重置比分
    public void reset(){
        String KEY_Ateam_NUMBER = getApplication().getResources().getString(R.string.KEY_Ateam_NUMBER);
        String KEY_Bteam_NUMBER = getApplication().getResources().getString(R.string.KEY_Bteam_NUMBER);
        aBack = getScore(KEY_Ateam_NUMBER).getValue();
        bBack = getScore(KEY_Bteam_NUMBER).getValue();
        handle.set(KEY_Ateam_NUMBER,0);
        handle.set(KEY_Bteam_NUMBER,0);
    }

    //撤销模块
    public void undo(){
        String KEY_Ateam_NUMBER = getApplication().getResources().getString(R.string.KEY_Ateam_NUMBER);
        String KEY_Bteam_NUMBER = getApplication().getResources().getString(R.string.KEY_Bteam_NUMBER);
        handle.set(KEY_Ateam_NUMBER,aBack);
        handle.set(KEY_Bteam_NUMBER,bBack);
    }

    public void save(){
        String KEY_Ateam_NUMBER = getApplication().getResources().getString(R.string.KEY_Ateam_NUMBER);
        String KEY_Bteam_NUMBER = getApplication().getResources().getString(R.string.KEY_Bteam_NUMBER);
        Save(KEY_Ateam_NUMBER);
        Save(KEY_Bteam_NUMBER);
    }
}
