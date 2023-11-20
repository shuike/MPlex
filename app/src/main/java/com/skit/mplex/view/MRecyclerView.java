package com.skit.mplex.view;

import static androidx.core.view.ViewCompat.TYPE_TOUCH;

import android.content.Context;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MRecyclerView extends RecyclerView {
    private boolean willRequestChildFocus = true;

    public MRecyclerView(@NonNull Context context) {
        super(context);
    }

    public MRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        willRequestChildFocus = true;
        super.requestChildFocus(child, focused);
        willRequestChildFocus = false;
    }

    private void handleFocusScroll(int dx, int dy) {
        if (dy < 0 && computeVerticalScrollOffset() > getHeight()) {//向上滑动并且当前的滚动位置大于高度的1/2不进行处理
            return;
        }
        int nestedScrollAxis = ViewCompat.SCROLL_AXIS_NONE | ViewCompat.SCROLL_AXIS_VERTICAL;
        startNestedScroll(nestedScrollAxis, TYPE_TOUCH);
        dispatchNestedPreScroll(dx, dy, new int[2], new int[2], TYPE_TOUCH);
        stopNestedScroll(TYPE_TOUCH);
    }

    @Override
    public void scrollBy(int x, int y) {
        handleFocusScroll(x, y);
        super.scrollBy(x, y);
    }

    @Override
    public void smoothScrollBy(int dx, int dy, @Nullable Interpolator interpolator, int duration) {
        handleFocusScroll(dx, dy);
        super.smoothScrollBy(dx, dy, interpolator, duration);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            int keyCode = event.getKeyCode();
            // 这里只考虑水平移动的情况（垂直移动相同的解决方案）
            if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                View focusedView = getFocusedChild();  // 获取当前获得焦点的view
                View nextFocusView;
                try {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                        // 通过findNextFocus获取下一个需要得到焦点的view
                        nextFocusView = FocusFinder.getInstance().findNextFocus(this, focusedView, View.FOCUS_LEFT);
                    } else {
                        // 通过findNextFocus获取下一个需要得到焦点的view
                        nextFocusView = FocusFinder.getInstance().findNextFocus(this, focusedView, View.FOCUS_RIGHT);
                    }
                } catch (Exception e) {
                    nextFocusView = null;
                }
                // 如果获取失败（也就是说需要交给系统来处理焦点， 消耗掉事件，不让系统处理， 并让先前获取焦点的view获取焦点）
                if (nextFocusView == null) {
                    focusedView.requestFocus();
                    return true;
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
