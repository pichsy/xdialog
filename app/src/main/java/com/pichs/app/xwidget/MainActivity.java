package com.pichs.app.xwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pichs.common.widget.cardview.XCardButton;
import com.pichs.common.widget.utils.XDisplayHelper;
import com.pichs.xdialog.CustomDialog;
import com.pichs.xdialog.action.OnPopupWindowDismissListener;
import com.pichs.xdialog.action.PopActions;
import com.pichs.xdialog.action.PopDialogActions;
import com.pichs.xdialog.base.BaseDialogFragment;
import com.pichs.xdialog.toast.PopToast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private AppCompatActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_main);
        XCardButton btn = findViewById(R.id.btn1);
        XCardButton btn2 = findViewById(R.id.btn2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomDialog.Builder(mActivity)
                        .setTitleTextColor(Color.BLUE)
                        .setTitleTextSize(24)
                        .setRadius(XDisplayHelper.dp2px(mActivity, 20f))
                        .setBackgroundColor(Color.YELLOW)
                        .setDimAmount(0.8f)
                        .setDividerLineColor(Color.RED)
                        .setNegativeTextColor(Color.parseColor("#FFFFFF"))
                        .setPositiveTextColor(Color.parseColor("#8F00F0"))
                        .setTitle("大家好")
                        .setMessage("我是小学生，我的技术非常好，带飞躺赢")
                        .setNegativeButtonText("滚一边去")
                        .setPositiveButtonText("上车")
                        .setOnNegativeClickListener(new CustomDialog.OnClickListener() {
                            @Override
                            public void onClick(BaseDialogFragment dialog, View view) {
                                dialog.dismiss();
                                new PopToast(mActivity)
                                        .setIconResId(R.mipmap.ic_launcher_round)
                                        .setMessage("大哥，给点面子，给个start好吗?")
                                        .setTitle("呜呜")
                                        .show(btn);
                            }
                        })
                        .setOnPositiveClickListener(new CustomDialog.OnClickListener() {
                            @Override
                            public void onClick(BaseDialogFragment dialog, View view) {
//                                new PopToast(mActivity)
//                                        .setIconResId(R.mipmap.ic_launcher_round)
//                                        .setMessage("大哥牛逼，赏个start吧！")
//                                        .setTitle("欧耶")
//                                        .show(btn);
                                TextView tv = new TextView(mActivity);
                                tv.setText("jdslfjldsajflasdjfldjsa");
                                tv.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
                                new PopDialogActions.Builder(mActivity)
                                        .setContentWidth(XDisplayHelper.dp2px(mActivity, 100f))
                                        .setContentHeight(XDisplayHelper.dp2px(mActivity, 50f))
                                        .setArrowHeight(XDisplayHelper.dp2px(mActivity, 8f))
                                        .setArrowWidth(XDisplayHelper.dp2px(mActivity, 16f))
                                        .setBackgroundColor(Color.RED)
                                        .setRadius(XDisplayHelper.dp2px(mActivity, 20f))
                                        .setOnPopupWindowDismissListener(new OnPopupWindowDismissListener() {
                                            @Override
                                            public void onPopDismiss() {

                                            }
                                        })
                                        .setContentView(tv)
                                        .setAnchor(view)
                                        .setAnimationStyle(R.style.XP_Animation_PopUpMenu_Center)
                                        .build()
                                        .show();

                            }
                        })
                        .build().show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMenu(v);
            }
        });
    }


    private void popMenu(View v) {
        TextView tv = new TextView(this);
        tv.setText("jdslfjldsajflasdjfldjsa");
        tv.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        new PopActions.Builder(this)
                .setContentWidth(XDisplayHelper.dp2px(this, 200f))
                .setContentHeight(XDisplayHelper.dp2px(this, 80f))
                .setArrowHeight(XDisplayHelper.dp2px(this, 8f))
                .setArrowWidth(XDisplayHelper.dp2px(this, 16f))
                .setBackgroundColor(Color.YELLOW)
                .setRadius(XDisplayHelper.dp2px(this, 20f))
                .setDimAmountEnable(true)
                .setOnPopupWindowDismissListener(new OnPopupWindowDismissListener() {
                    @Override
                    public void onPopDismiss() {

                    }
                })
                .setContentView(tv)
                .setAnchor(v)
                .setOffsetY(-110)
                .setAnimationStyle(R.style.XP_Animation_PopUpMenu_Center)
                .build()
                .show();
    }
}