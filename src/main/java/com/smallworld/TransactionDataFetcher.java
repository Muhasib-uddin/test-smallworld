package com.smallworld;

import com.smallworld.data.Transaction;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class TransactionDataFetcher {

    public List<Transaction> transactionList;
    public TransactionDataFetcher(List<Transaction> transactionList){
        this.transactionList = transactionList;
    }

    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount() {
        double sum = 0;
        for(Transaction transaction: this.transactionList){
            sum=sum+transaction.getAmount();
        }
        return sum;
//        return this.transactionList.stream().mapToDouble(Transaction::getAmount).sum();
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) {
        return this.transactionList.stream().filter(transaction -> transaction.getSenderFullName().equals(senderFullName)).mapToDouble(Transaction::getAmount).sum();
    }

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount() {
        return   this.transactionList.stream().sorted(comparing(Transaction::getAmount)
                .reversed()).toList().get(0).getAmount();
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients() {
        HashSet<String> senderAndBeneficiariesUnique = new HashSet<>();
        this.transactionList.forEach(transaction -> {
            senderAndBeneficiariesUnique.add(transaction.getSenderFullName());
            senderAndBeneficiariesUnique.add(transaction.getBeneficiaryFullName());
        });
        return senderAndBeneficiariesUnique.size();

    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName) {
        List<Transaction> transactionsSorted = new ArrayList<>();
        this.transactionList.forEach(transaction -> {
            if (clientFullName.equals(transaction.getSenderFullName()) ||
                    clientFullName.equals(transaction.getBeneficiaryFullName())){
                transactionsSorted.add(transaction);
            }
        });
        long countOpenComplianceIssue = transactionsSorted.stream().filter(transaction -> !transaction.getIssueSolved())
                .count();
        return countOpenComplianceIssue >= 1;
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, Transaction> getTransactionsByBeneficiaryName() {
        Map<String,Transaction> indexedBeneficiary = new HashMap<>();
        this.transactionList.forEach(transaction -> indexedBeneficiary.
                put(transaction.getBeneficiaryFullName(),transaction));
        return indexedBeneficiary;

    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds() {
        Set<Integer> unsolvedIssueIds = new HashSet<>();
        this.transactionList.forEach(transaction -> {
            if (transaction.getIssueId()!= null && !transaction.getIssueSolved()){
                unsolvedIssueIds.add(transaction.getIssueId());
            }
        });
        return unsolvedIssueIds;
    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages() {
        List<String> solvedIssueMessagesList = new ArrayList<>();
        this.transactionList.forEach(transaction -> {
            if(transaction.getIssueSolved()){
                solvedIssueMessagesList.add(transaction.getIssueMessage());
            }
        });
        return solvedIssueMessagesList;
    }

    /**
     * Returns the 3 transactions with the highest amount sorted by amount descending
     */
    public List<Transaction> getTop3TransactionsByAmount() {
        return this.transactionList.stream().sorted(comparing(Transaction::getAmount)
                .reversed()).limit(3).toList();
    }

    /**
     * Returns the senderFullName of the sender with the most total sent amount
     */
    public String getTopSender() {
        Map<String,Double> sendersAndTotalAmounts = new HashMap<>();
        this.transactionList.forEach(transaction -> {
                if(sendersAndTotalAmounts.containsKey(transaction.getSenderFullName())){
                    sendersAndTotalAmounts.put(transaction.getSenderFullName() , sendersAndTotalAmounts
                            .get(transaction.getSenderFullName())+transaction.getAmount());
                }else{
                    sendersAndTotalAmounts.put(transaction.getSenderFullName() , transaction.getAmount());
                }
        });
        Map.Entry<String,Double> maxEntry = null;
        for(Map.Entry<String,Double> entry : sendersAndTotalAmounts.entrySet()){
            if(maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0 ){
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();

    }

}
