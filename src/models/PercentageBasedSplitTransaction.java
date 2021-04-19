package models;

import java.util.List;

public class PercentageBasedSplitTransaction extends Transaction{

    public PercentageBasedSplitTransaction(String transactionName, User paidBy,
                                           List<PercentageBasedSplitTransactionDTO> percentageBasedSplitTransactionDTOS, Float amount) throws Exception {
        super(transactionName, paidBy, amount);
        if (!validate(percentageBasedSplitTransactionDTOS)) {
            throw new Exception("Percentage Based Split not valid");
        }
        split(percentageBasedSplitTransactionDTOS);
    }

    private void split(List<PercentageBasedSplitTransactionDTO> percentageBasedSplitTransactionDTOS) {
        for (PercentageBasedSplitTransactionDTO percentageBasedSplitTransactionDTO : percentageBasedSplitTransactionDTOS) {
            this.amountMapping.put(percentageBasedSplitTransactionDTO.getUser(),
                    percentageBasedSplitTransactionDTO.getPercentage()/100F * this.amount);
        }
    }

    public boolean validate(List<PercentageBasedSplitTransactionDTO> percentageBasedSplitTransactionDTOS) {
        Float total = 0F;

        for (PercentageBasedSplitTransactionDTO percentageBasedSplitTransactionDTO : percentageBasedSplitTransactionDTOS) {
            total += percentageBasedSplitTransactionDTO.getPercentage()/100F * this.amount;
        }

        return total.equals(this.amount);
    }

}
