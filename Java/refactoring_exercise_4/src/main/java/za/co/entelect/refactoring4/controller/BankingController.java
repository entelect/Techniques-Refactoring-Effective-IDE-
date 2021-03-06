package za.co.entelect.refactoring4.controller;

import za.co.entelect.refactoring4.domain.AccountType;
import za.co.entelect.refactoring4.domain.BankAccount;
import za.co.entelect.refactoring4.domain.BankingAction;
import za.co.entelect.refactoring4.domain.Image;
import za.co.entelect.refactoring4.exception.BankAccountException;

/*
 * Exercise 4
 *
 * The Banking controller suffers from the following issues
 *
 * 1. Switch statements : Refactoring to a pattern, for a example of the strategy pattern see StrategyExample
 * 2  Middle Man :
 * 3. There is one more smell in this class?
 *
 */
public class BankingController {

    private static final long ACCOUNT_REOPEN_FEE_CENTS = 2000;

    private ImageServiceDelegate imageServiceDelegate = new ImageServiceDelegate();

    public Image fetchImage(String id) {
        return imageServiceDelegate.fetch(id);
    }

    public Image uploadImage(String id, byte[] data){
        Image image = new Image(id, data);
        imageServiceDelegate.add(image);
        return image;
    }

    public void updateAccount(BankAccount bankAccount, BankingAction bankingAction){
        switch (bankingAction){
            case RECALCULATE_BALANCE :
                bankAccount.calculateInterest();
                break;
            case CLOSE_ACCOUNT :
                if(bankAccount.isAccountActive()){
                    bankAccount.closeAccount();
                }
                break;
            case REOPEN_ACCOUNT:
                if(!bankAccount.isAccountActive()){
                    if(AccountType.CHEQUE != bankAccount.getAccountType() && bankAccount.getBalanceInCents() - ACCOUNT_REOPEN_FEE_CENTS < 0){
                        throw new BankAccountException("Account does not contain a balance to debit the reopen fee");
                    }else{
                        bankAccount.updateBalance(-ACCOUNT_REOPEN_FEE_CENTS);
                        bankAccount.reopenAccount();
                    }
                }
                break;
            case CHARGE_ACCOUNT_FEE:
                bankAccount.calculateBalance(-bankAccount.getFeeInCents());
            break;
        }
    }

}