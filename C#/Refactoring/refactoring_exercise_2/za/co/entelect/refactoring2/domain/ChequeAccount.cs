namespace refactoring_exercise_2.za.co.entelect.refactoring2.domain
{

    public class ChequeAccount : BankAccount
    {

        public ChequeAccount(long balanceInCents, double creditInterestsRate, double debitInterestRate, long accountFee) :
            base(balanceInCents, creditInterestsRate, debitInterestRate, accountFee)
        {
        }

        public override AccountType GetAccountType()
        {
            return AccountType.Cheque;
        }
    }
}