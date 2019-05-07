package com.sinothk.android.utils;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@Deprecated
public class AViewUtil extends ViewUtil {
    /**
     * 输入框内容提示
     *
     * @param context
     * @param editText
     * @param max_length
     * @param err_msg
     */
    public static void inputMaxTip(final Context context, final EditText editText, final TextView inputMaxTipTv, final int max_length, final String err_msg) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                int len = s.toString().length();

                inputMaxTipTv.setText(len + "/" + max_length);
            }
        });

        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(max_length) {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                int destLen = dest.toString().length();
                int sourceLen = source.toString().length();

                if (destLen + sourceLen > max_length + 1) {
                    Toast.makeText(context, err_msg, Toast.LENGTH_SHORT).show();
                    return "";
                }
                return source;
            }
        };
        editText.setFilters(filters);
    }

    /**
     * @param content
     * @return
     * @description 获取一段字符串的字符个数(包含中英文, 一个中文算2个字符)
     */
    private static int getCharacterNum(final String content) {
        if (null == content || "".equals(content)) {
            return 0;
        } else {
            return (content.length() + getChineseNum(content));
        }
    }

    /**
     * @param s
     * @return
     * @description 返回字符串里中文字或者全角字符的个数
     */
    private static int getChineseNum(String s) {

        int num = 0;
        char[] myChar = s.toCharArray();
        for (int i = 0; i < myChar.length; i++) {
            if ((char) (byte) myChar[i] != myChar[i]) {
                num++;
            }
        }
        return num;
    }
}
