package models;

import java.util.List;

public class FixedValuesTransaction extends Transaction {

    public FixedValuesTransaction(String transactionName, User paidBy, List<FixedValuesTransactionDTO> fixedValuesTransactionDTOS, Float amount) throws Exception {
        super(transactionName, paidBy, amount);
        if (!validate(fixedValuesTransactionDTOS)) {
            throw new Exception("Fixed values split is invalid");
        }
        split(fixedValuesTransactionDTOS);
    }

    private void split(List<FixedValuesTransactionDTO> fixedValuesTransactionDTOS) {
        for (FixedValuesTransactionDTO fixedValuesTransactionDTO : fixedValuesTransactionDTOS) {
            this.amountMapping.put(fixedValuesTransactionDTO.getUser(), fixedValuesTransactionDTO.getValue());
        }
    }

    private boolean validate(List<FixedValuesTransactionDTO> fixedValuesTransactionDTOS) {
        Float total = 0F;

        for (FixedValuesTransactionDTO fixedValuesTransactionDTO : fixedValuesTransactionDTOS) {
            total += fixedValuesTransactionDTO.getValue();
        }

        return total.equals(this.amount);
    }

}