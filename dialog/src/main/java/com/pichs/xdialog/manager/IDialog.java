package com.pichs.xdialog.manager;


/**
 * @Description: $
 * @Author: WuBo
 * @CreateDate: 2020/11/10$ 11:15$
 * @UpdateUser: WuBo
 * @UpdateDate: 2020/11/10$ 11:15$
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface IDialog {

    long currentMills = System.currentTimeMillis();

    void show();

    void dismiss();

    void setIDismissListener(IDismissListener dismissListener);

    // 获取优先级，弹窗会有一个优先级, 同一优先级，后添加的先显示（不符合逻辑）
    long getPriority();

    boolean isShowing();
}
