package com.example.user.mychat.utils;

import android.view.View;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringUtil;

public class LoginAnimationListener implements SpringListener {

        private View mLogoView, mLoginView;

        public LoginAnimationListener(View logoView, View loginView){
            mLogoView = logoView;
            mLoginView = loginView;
        }
        
        @Override
        public void onSpringUpdate(Spring spring) {
            if (mLogoView != null && mLoginView != null) {
                double value = spring.getCurrentValue();
                float barPosition =
                        (float) SpringUtil.mapValueFromRangeToRange(value, 0, 1, 500, 0);
                mLogoView.setTranslationY(-barPosition);
            }
            if(spring.getCurrentValue() == 1) {
                mLoginView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onSpringAtRest(Spring spring) {

        }

        @Override
        public void onSpringActivate(Spring spring) {

        }

        @Override
        public void onSpringEndStateChange(Spring spring) {

        }
    }