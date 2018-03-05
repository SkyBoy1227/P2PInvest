package com.sky.app.p2pinvest.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sky.app.p2pinvest.R;

/**
 * Created with Android Studio.
 * 描述: 主页
 * Date: 2018/3/5
 * Time: 21:01
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_home, null);
        return view;
    }
}
