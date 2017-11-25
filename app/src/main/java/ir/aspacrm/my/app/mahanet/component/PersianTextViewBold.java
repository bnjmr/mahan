package ir.aspacrm.my.app.mahanet.component;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import ir.aspacrm.my.app.mahanet.G;

public  class PersianTextViewBold extends TextView {
    FontHelper fontHelper = new FontHelper();
    public PersianTextViewBold(Context context) {
        super(context);
        if (!isInEditMode())
            setTypeface(fontHelper.getInstance(context,"fonts/rezvan.ttf").getPersianTextTypeface());
    }
    public PersianTextViewBold(Context context, AttributeSet attrs) {
        super(G.context, attrs);
        if (!isInEditMode())
            setTypeface(fontHelper.getInstance(G.context,"fonts/rezvan.ttf").getPersianTextTypeface());
    }
    public PersianTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode())
            setTypeface(fontHelper.getInstance(context,"fonts/rezvan.ttf").getPersianTextTypeface());
    }
    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text != null)
            text = FormatHelper.toPersianNumber(text.toString());
        super.setText(text, type);
    }
}
