package com.smallworld;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.data.Transaction;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TransactionDataFetcherTest {
    private List<Transaction> transactionList;
    private TransactionDataFetcher dataFetcher;

    @BeforeEach
    public void setUp() throws IOException {
        //Read Json to Array List
        ObjectMapper objectMapper = new ObjectMapper();
        transactionList = objectMapper.readValue(new File("../coding_test/transactions.json"), new TypeReference<>() {
        });
        // Create a data fetcher object
        dataFetcher = new TransactionDataFetcher(transactionList);
    }

    @Test
    public void testGetTotalTransactionAmount() {
        double expectedTotalAmount = 1300.0;
        double actualTotalAmount = dataFetcher.getTotalTransactionAmount();
        Assertions.assertEquals(expectedTotalAmount, actualTotalAmount);
    }

    @Test
    public void testGetTotalTransactionAmountSentBy() {
        double expectedTotalAmount = 25.0;
        double actualTotalAmount = dataFetcher.getTotalTransactionAmountSentBy("Bat Man");
        Assertions.assertEquals(expectedTotalAmount, actualTotalAmount);
    }

    @Test
    public void testGetMaxTransactionAmount() {
        double expectedMaxAmount = 350.0;
        double actualMaxAmount = dataFetcher.getMaxTransactionAmount();
        Assertions.assertEquals(expectedMaxAmount, actualMaxAmount);
    }

    @Test
    public void testCountUniqueClients() {
        long expectedCount = 2;
        long actualCount = dataFetcher.countUniqueClients();
        Assertions.assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testHasOpenComplianceIssues() {
        boolean hasOpenIssues = dataFetcher.hasOpenComplianceIssues("Tom Shelby");
        Assertions.assertTrue(hasOpenIssues);
    }

    @Test
    public void testGetTransactionsByBeneficiaryName() {
        Map<String, Transaction> expectedIndexedBeneficiary = new HashMap<>();
        expectedIndexedBeneficiary.put("Aurther Shelby" , new Transaction(6l,1111.2,"Joker", 50,"Blur",56,8,false,"Opps Its not working"));
        Map<String, Transaction> actualIndexedBeneficiary = dataFetcher.getTransactionsByBeneficiaryName();
        Assertions.assertEquals(expectedIndexedBeneficiary, actualIndexedBeneficiary);
    }

    @Test
    public void testGetUnsolvedIssueIds() {
        Transaction transaction9 = new Transaction(6l,1111.2,"Joker", 50,"Blur",56,1,false,"Opps Its not working");
        Transaction transaction10 = new Transaction(6l,1111.2,"Joker", 50,"Blur",56,8,false,"Opps Its not working");
        transactionList.addAll(Arrays.asList(transaction9, transaction10));
        Set<Integer> expectedId = new HashSet<>();
        expectedId.add(1);
        expectedId.add(2);
        Set<Integer> actualId = dataFetcher.getUnsolvedIssueIds();
        Assertions.assertEquals(expectedId, actualId);
    }

    @Test
    public void testGetAllSolvedIssueMessages() {

        List<String> expectedMessages = new ArrayList<>();
        expectedMessages.add("hello it was a success");
        expectedMessages.add("hello! welldone");
        List<String> actualMessages = dataFetcher.getAllSolvedIssueMessages();
        Assertions.assertEquals(expectedMessages, actualMessages);
    }

    @Test
    public void testGetTop3TransactionsByAmount() {
        Transaction transaction11 = new Transaction(6l,1111.2,"Joker", 50,"Blur",56,8,false,"Opps Its not working");
        Transaction transaction12 = new Transaction(6l,1111.2,"Joker", 50,"Blur",56,8,false,"Opps Its not working");
        List<Transaction> expectedTop3Transaction = new ArrayList<>(Arrays.asList(transaction11, transaction12));
        List<Transaction> actualTop3Transactions = dataFetcher.getTop3TransactionsByAmount();
        Assertions.assertEquals(expectedTop3Transaction, actualTop3Transactions);
    }

    @Test
    public void testGetTopSender() {
        String expectedTopSender = "Tom Shelby";
        String actualTopSender = dataFetcher.getTopSender();
        Assertions.assertEquals(expectedTopSender, actualTopSender);
    }
}
