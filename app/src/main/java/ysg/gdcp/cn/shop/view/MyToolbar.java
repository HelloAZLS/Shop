
package ysg.gdcp.cn.shop.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ysg.gdcp.cn.shop.R;


/**
 * Created by Administrator on 2017/6/1 20:50.
 *
 * @author ysg
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class MyToolbar extends Toolbar {
    private LayoutInflater mInflater;
    private TextView mTvTitle;
    private EditText mEtTxt;
    private Button mRightButton;
    private View mView;

    public MyToolbar(Context context) {
        this(context, null);
    }


    public MyToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public MyToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
        setContentInsetsRelative(10, 10);
        if (attrs != null) {
            TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.MyToolbar, defStyleAttr, 0);
            Drawable drawable = a.getDrawable(R.styleable.MyToolbar_rightButtionIcon);
            if (drawable != null) {
                setRightButtionIcon(drawable);
            }

            boolean isShowSearchView = a.getBoolean(R.styleable.MyToolbar_isShowSearchView, false);
            if (isShowSearchView) {
                showSearchView();
                hideTitleView();
            } else {
                showTitleView();
                hideSearchView();
            }
            a.recycle();
        }


    }

    private void initViews() {
        if (mView == null) {
            mView = View.inflate(getContext(), R.layout.toolbar, null);
            mTvTitle = (TextView) mView.findViewById(R.id.toolbar_title);
            mEtTxt = (EditText) mView.findViewById(R.id.toolbar_searchview);
            mRightButton = (Button) mView.findViewById(R.id.toolbar_rightButton);

            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView, params);
        }
    }


    public void setRightButtionIcon(Drawable drawable) {
        if (drawable != null) {
            mRightButton.setBackground(drawable);
            mRightButton.setVisibility(VISIBLE);
        }
    }

    public void setRightButtionONclickListener(OnClickListener listener) {
        mRightButton.setOnClickListener(listener);
    }

    public Button getRightButton() {
        return this.mRightButton;
    }

    @Override
    public void setTitle(int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        initViews();
        if (mTvTitle != null) {
            mTvTitle.setText(title);
            showTitleView();
        }
    }

    public void showSearchView() {
        if (mEtTxt != null) {
            mEtTxt.setVisibility(VISIBLE);
        }
    }

    public void hideSearchView() {
        if (mEtTxt != null) {
            mEtTxt.setVisibility(GONE);
        }
    }

    public void showTitleView() {
        if (mTvTitle != null) {
            mTvTitle.setVisibility(VISIBLE);
        }
    }

    public void hideTitleView() {
        if (mTvTitle != null) {
            mTvTitle.setVisibility(GONE);
        }
    }
}
