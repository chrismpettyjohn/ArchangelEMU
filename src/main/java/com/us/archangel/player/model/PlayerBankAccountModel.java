package com.us.archangel.player.model;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerBankAccountModel {

    private int id;
    private int userId;
    private int corpId;
    private int accountBalance;

    public void addAccountBalance(int credits) {
        this.accountBalance += credits;
    }
    public void deductAccountBalance(int credits) {
        this.accountBalance -= credits;
    }
}
