package com.gp.gifshot.plugs.tab.fragment;
import com.gp.gifshot.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * Description：
 * Created by：CaMnter
 * Time：2015-10-17 12:15
 */
public class SecondFragment extends Fragment {

    private View self;

    private static SecondFragment instance;

    private SecondFragment() {
    }

    public static SecondFragment getInstance() {
        if (instance == null) instance = new SecondFragment();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	System.out.println("第二个tab的onCreateView");
        if (this.self == null) {
            this.self = inflater.inflate(R.layout.second_fragment, null);
        }
        if (this.self.getParent() != null) {
            ViewGroup parent = (ViewGroup) this.self.getParent();
            parent.removeView(this.self);
        }
        return this.self;
    }
}
