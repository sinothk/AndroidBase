package com.sinothk.android.utils.bean;

/**
 * <pre>
 *  创建:  梁玉涛 2019/5/7 on 13:58
 *  项目:  AndroidBaseLib
 *  描述:
 *  更新:
 * <pre>
 */
public class Bank {

    String bankId;
    String bankName;

    public Bank(String bankId, String bankName) {
        this.bankId = bankId;
        this.bankName = bankName;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
