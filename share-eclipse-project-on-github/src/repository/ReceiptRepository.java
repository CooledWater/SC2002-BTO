package repository;

import entity.Receipt;

import java.util.ArrayList;
import java.util.List;

public class ReceiptRepository extends Repository {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1432534849144426795L;
	private List<Receipt> receipts = new ArrayList<>();

    public void addReceipt(Receipt receipt) {
        receipts.add(receipt);
    }

    public List<Receipt> getAllReceipts() {
        return receipts;
    }
    
    public void markAsWithdrawn(String nric) {
        for (Receipt r : receipts) {
            if (r.getApplicantNRIC().equalsIgnoreCase(nric)) {
                r.setWithdrawn(true);
            }
        }
    }

    public void printAllReceipts() {
        if (receipts.isEmpty()) {
            System.out.println("No receipts available.");
            return;
        }

        for (Receipt receipt : receipts) {
            System.out.println(receipt);
        }
    }

    public void printReceiptsByNric(String nric) {
        boolean found = false;
        for (Receipt r : receipts) {
            if (r.getApplicantNRIC().equalsIgnoreCase(nric)) {
                System.out.println(r);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No receipt found for NRIC: " + nric);
        }
    }
}