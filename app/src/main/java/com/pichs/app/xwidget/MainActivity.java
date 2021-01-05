package com.pichs.app.xwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.pichs.common.widget.cardview.XCardButton;
import com.pichs.xdialog.CustomDialog;
import com.pichs.xdialog.base.BaseDialogFragment;
import com.pichs.xdialog.toast.PopToast;

public class MainActivity extends AppCompatActivity {

    private AppCompatActivity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_main);
        XCardButton btn = findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomDialog.Builder(mActivity)
                        .setTitle("大家好")
                        .setMessage("我是小学生，我的技术非常好，带飞躺赢")
                        .setCancelButtonText("滚一边去")
                        .setConfirmButtonText("上车")
                        .setOnCancelClickListener(new CustomDialog.OnClickListener() {
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
                        .setOnConfirmClickListener(new CustomDialog.OnClickListener() {
                            @Override
                            public void onClick(BaseDialogFragment dialog, View view) {
                                dialog.dismiss();
                                new PopToast(mActivity)
                                        .setIconResId(R.mipmap.ic_launcher_round)
                                        .setMessage("大哥牛逼，赏个start吧！")
                                        .setTitle("欧耶")
                                        .show(btn);
                            }
                        })
                        .build().show();
            }
        });
    }
}