package com.quickstart.widgets;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created at 08.12.16 13:58
 *
 * @author Alexey_Ivanov
 */

public class RefreshLayout extends android.support.v4.widget.SwipeRefreshLayout {

    private int[] pullableIds = new int[0];

    public RefreshLayout(Context context) {

        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    public void setPullableIds(int... ids) {

        pullableIds = ids;
    }

    @Override
    public boolean canChildScrollUp() {

        for (int id : pullableIds) {
            View v = findViewById(id);
            if (v == null)
                continue;
//            if (android.os.Build.VERSION.SDK_INT < 14) {
//                if (v instanceof AbsListView) {
//                    AbsListView absListView = (AbsListView) v;
//                    return absListView.getChildCount() > 0
//                           && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
//                                                                                       .getTop() < absListView.getPaddingTop());
//                }
//                else if (v instanceof ScrollView) {
//                    ScrollView scrollView = (ScrollView) v;
//                    return scrollView.getScrollY() > 0;
//                }
//                else {
//                    return super.canChildScrollUp();
//                }
//            }
//            else {
                return ViewCompat.canScrollVertically(v, -1);
//            }
        }

        return true;
    }
}
