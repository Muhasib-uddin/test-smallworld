package com.smallworld;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.data.Transaction;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main{

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Transaction> transactionList = objectMapper.readValue(new File("coding_test/transactions.json"), new TypeReference<>() {
        });
        TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher(transactionList);
        double totalTransactions = transactionDataFetcher.getTotalTransactionAmount();
        double totalTransactionsOfSender = transactionDataFetcher.getTotalTransactionAmountSentBy("Grace Burgess");
        double maxTransactionsAmount = transactionDataFetcher.getMaxTransactionAmount();
        long totalSenderAndReceiver = transactionDataFetcher.countUniqueClients();
        boolean clientComplianceIssue = transactionDataFetcher.hasOpenComplianceIssues("Luca Changretta");
        Map<String ,Transaction> transactionsByBeneficiaries = transactionDataFetcher.getTransactionsByBeneficiaryName();
        Set<Integer> unsolvedIssueIds = transactionDataFetcher.getUnsolvedIssueIds();
        List<String> solvedIssueMessageList = transactionDataFetcher.getAllSolvedIssueMessages();
        List<Transaction> top3TransactionsByAmountList = transactionDataFetcher.getTop3TransactionsByAmount();
        String senderFullNameHighestAmount = transactionDataFetcher.getTopSender();

        System.out.println("Total transactions " + totalTransactions);

        System.out.println("Total  Transaction of Specific Client " + totalTransactionsOfSender);

        System.out.println("Maximum  Transaction Amount " + maxTransactionsAmount);

        System.out.println("Total Sender Or Receiver " + totalSenderAndReceiver);

        System.out.println("Client Compliance Issues " + clientComplianceIssue);

        System.out.println("Transactions By Beneficiaries " + transactionsByBeneficiaries);

        System.out.println("All Unsolved Issue Ids " + unsolvedIssueIds);

        System.out.println("All solved Issue messages " + solvedIssueMessageList);

        System.out.println("Top 3 Transactions Of highest Amount are: " + top3TransactionsByAmountList);

        System.out.println("Highest Total Amount of sender:  " + senderFullNameHighestAmount);


    }
}
