package com.pichs.xdialog.manager;



import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @Description: $
 * @Author: WuBo
 * @CreateDate: 2020/11/10$ 11:22$
 * @UpdateUser: WuBo
 * @UpdateDate: 2020/11/10$ 11:22$
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DialogManager {
    //弹窗队列(线程安全)，Priority约大 优先级越高。
    private final PriorityBlockingQueue<IDialog> dialogQueue = new PriorityBlockingQueue<>(1, new Comparator<IDialog>() {
        @Override
        public int compare(IDialog d1, IDialog d2) {
            return Long.compare(d2.getPriority(), d1.getPriority());
        }
    });
    private IDialog mCurrentDialog;

    private DialogManager() {
    }

    private interface Holder {
        DialogManager instance = new DialogManager();
    }

    public static DialogManager get() {
        return Holder.instance;
    }


    public void push(IDialog dialog) {
        if (dialog != null) {
            dialog.setIDismissListener(new IDismissListener() {
                @Override
                public void onDismiss(IDialog dialog) {
                    dialogQueue.remove(dialog);
                    nextShow();
                }
            });
            dialogQueue.add(dialog);
            if (isCanShow()) {
                nextMaybeShow();
            }
        }
    }

    private void nextShow() {
        nextMaybeShow();
    }

    private void nextMaybeShow() {
        if (dialogQueue.isEmpty()) {
            return;
        }
        mCurrentDialog = dialogQueue.element();
        if (mCurrentDialog != null) {
            mCurrentDialog.show();
        } else {
        }
    }

    private boolean isCanShow() {
        return dialogQueue.size() < 2;
//        if (mCurrentDialog == null) return true;
//        if (mCurrentDialog.isShowing()) {
//            return false;
//        }
//        return !dialogQueue.isEmpty();
    }

    private void removeTop() {
        dialogQueue.poll();
    }

    public void clearAll() {
        dialogQueue.clear();
        if (mCurrentDialog != null) {
            if (mCurrentDialog.isShowing()) {
                mCurrentDialog.dismiss();
            }
            mCurrentDialog = null;
        }
    }
}
