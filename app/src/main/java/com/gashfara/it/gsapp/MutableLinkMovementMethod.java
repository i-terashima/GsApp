package com.gashfara.it.gsapp;

import android.net.Uri;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by it on 2016/10/01.
 */
public class MutableLinkMovementMethod extends LinkMovementMethod {

    /**
     * Url�̃����N���^�b�v�������̃C�x���g���󂯎�郊�X�i�[
     *
     */
    public interface OnUrlClickListener{
        public abstract void onUrlClick(TextView widget, Uri uri);
    }

    /** Url�N���b�N���X�i�[ */
    OnUrlClickListener listener = null;

    /*
     * Url�N���b�N���X�i�[��o�^
     */
    public void setOnUrlClickListener(OnUrlClickListener l){
        listener = l;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer,
                                MotionEvent event) {

        // LinkMovementMethod#onTouchEvent���̂܂��

        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    // ���X�i�[������΂�������Ăяo��
                    if(link[0] instanceof URLSpan && listener!=null){
                        Uri uri = Uri.parse( ((URLSpan)link[0]).getURL() );
                        listener.onUrlClick(widget, uri);
                    }else{
                        link[0].onClick(widget);
                    }
                } else if (action == MotionEvent.ACTION_DOWN) {
                    Selection.setSelection(buffer,
                            buffer.getSpanStart(link[0]),
                            buffer.getSpanEnd(link[0]));
                }

                return true;
            } else {
                Selection.removeSelection(buffer);
            }
        }

        return false;//super.onTouchEvent(widget, buffer, event);
    }
}