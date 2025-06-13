package com.avis.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.avis.entity.CoalTransaction;
import com.avis.repository.CoalTransactionRepository;


@Service
public class CoalTransactionService {

	@Autowired
    private CoalTransactionRepository repository;

	
	
  //-------
	public CoalTransaction save(CoalTransaction txn) {
	    boolean isNew = (txn.getId() == null);

	    if (txn.getDate() == null) {
	        txn.setDate(LocalDate.now());
	    }

	    if (isNew) {
	        // If invoiceNo is empty, auto-generate it
	        if (txn.getInvoiceNo() == null || txn.getInvoiceNo().trim().isEmpty()) {
	            LocalDate txnDate = txn.getDate();
	            int fyStartYear = txnDate.getMonthValue() >= 4 ? txnDate.getYear() : txnDate.getYear() - 1;
	            int fyEndYear = fyStartYear + 1;
	            String fyString = fyStartYear + "-" + String.format("%02d", fyEndYear % 100);

	            LocalDate startDate = LocalDate.of(fyStartYear, 4, 1);
	            LocalDate endDate = LocalDate.of(fyEndYear, 3, 31);

	            long count = repository.countByDateBetween(startDate, endDate) + 1;
	            String sequence = String.format("%03d", count);
	            String invoiceNo = fyString + "/" + sequence;

	            txn.setInvoiceNo(invoiceNo);

	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");
	            txn.setRefNo(invoiceNo + " dt. " + txn.getDate().format(formatter));
	        } else {
	            // User provided invoice number, generate default refNo if missing
	            if (txn.getRefNo() == null || txn.getRefNo().trim().isEmpty()) {
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");
	                txn.setRefNo(txn.getInvoiceNo() + " dt. " + txn.getDate().format(formatter));
	            }
	        }
	    } else {
	        // For update: retain manually entered invoice/ref or allow edits
	        CoalTransaction existing = repository.findById(txn.getId()).orElse(null);
	        if (existing != null) {
	            if (txn.getInvoiceNo() == null || txn.getInvoiceNo().trim().isEmpty()) {
	                txn.setInvoiceNo(existing.getInvoiceNo());
	            }
	            if (txn.getRefNo() == null || txn.getRefNo().trim().isEmpty()) {
	                txn.setRefNo(existing.getRefNo());
	            }
	        }
	    }

	 // ✅ Extract numeric invoice sequence for sorting (e.g., 2024-25/102 → 102)
	    if (txn.getInvoiceNo() != null && txn.getInvoiceNo().contains("/")) {
	        try {
	            String seqPart = txn.getInvoiceNo().split("/")[1];
	            txn.setInvoiceSeq(Integer.parseInt(seqPart));
	        } catch (Exception e) {
	            txn.setInvoiceSeq(0); // fallback if parsing fails
	        }
	    }
	    
	    // GST Calculations
	    BigDecimal amount = txn.getWeight().multiply(txn.getRate());
	    BigDecimal gstAmount = amount.multiply(txn.getGstPercent()).divide(new BigDecimal("100"));

	    // Round to nearest rupee
	    BigDecimal roundedGst = new BigDecimal(Math.round(gstAmount.doubleValue()));
	    BigDecimal total = amount.add(roundedGst);
	    BigDecimal roundedTotal = new BigDecimal(Math.round(total.doubleValue()));

	    txn.setAmount(amount);
	    txn.setGstAmount(roundedGst);
	    txn.setTotalAmount(roundedTotal);
	    txn.setTotalAmountInWords(convertToWords(roundedTotal.longValue()) + " Only");

	    return repository.save(txn);
	}

	
	//--------
    
    public CoalTransaction getById(Long id) {
        return repository.findById(id).orElse(null);
    }
    
    // pagination method for index.html( to see sales transaction)
    public Page<CoalTransaction> getPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, org.springframework.data.domain.Sort.by("invoiceNo").ascending());
        return repository.findAll(pageable);
    }

    
 // Simple Indian-style amount-to-words converter
    public String convertToWords(long number) {
        final String[] units = {
            "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
            "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen",
            "Sixteen", "Seventeen", "Eighteen", "Nineteen"
        };
        final String[] tens = {
            "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
        };

        if (number == 0) return "Zero";

        StringBuilder words = new StringBuilder();

        if ((number / 10000000) > 0) {
            words.append(convertToWords(number / 10000000)).append(" Crore ");
            number %= 10000000;
        }
        if ((number / 100000) > 0) {
            words.append(convertToWords(number / 100000)).append(" Lakh ");
            number %= 100000;
        }
        if ((number / 1000) > 0) {
            words.append(convertToWords(number / 1000)).append(" Thousand ");
            number %= 1000;
        }
        if ((number / 100) > 0) {
            words.append(convertToWords(number / 100)).append(" Hundred ");
            number %= 100;
        }
        if (number > 0) {
            if (words.length() > 0) words.append("and ");
            if (number < 20) words.append(units[(int) number]);
            else {
                words.append(tens[(int) number / 10]);
                if ((number % 10) > 0) {
                    words.append("-").append(units[(int) number % 10]);
                }
            }
        }

        return words.toString().trim();
    }
    
    // business logic to download pdf
    
    

}