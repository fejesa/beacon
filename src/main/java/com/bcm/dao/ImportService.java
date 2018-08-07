package com.bcm.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bcm.pojo.BankAccountant;
import com.bcm.pojo.Customer;

@Service("ImportService")
@Transactional
public class ImportService {

	private static final String UTF8_BOM = "\uFEFF";

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	@PersistenceContext
	private EntityManager em;

	public void importData(Path source) throws IOException, ParseException {
		try (Scanner scanner = new Scanner(source);) {

			removeUTF8BOM(scanner.nextLine());

			// 0. "KUID";
			// 1. "TITEL";
			// 2. "NACHNAME";
			// 3. "VORNAME";
			// 4. "GEBDAT";
			// 5. "EMAIL";
			// 6. "ORT";
			// 7. "STRASSE";
			// 8. "PLZ";
			// 9. "KUBERUFBRNCH";
			// 10. "KONTOSTAND";
			// 11. "BONUSPUNKTE";
			// 12. "BLZ";
			// 13. "BETREUER";
			// 14. "NPNACHNAME";
			// 15. "NPVORNAME";
			// 16. "TELEFON";
			// 17. "EMAIL
			for (; scanner.hasNext();) {
				String line = scanner.nextLine();
				String[] tokens = getTokens(line);

				BankAccountant bankAccountant = getAccountant(tokens[13]);
				if (bankAccountant == null) {
					bankAccountant = new BankAccountant();
					bankAccountant.setAccountantId(tokens[13]);
					em.persist(bankAccountant);
					em.flush();
				}
				bankAccountant.setLastName(tokens[14]);
				bankAccountant.setFirstName(tokens[15]);
				bankAccountant.setPhone(tokens[16]);
				bankAccountant.setEmail(tokens[17]);

				em.merge(bankAccountant);

				Customer customer = getCustomer(tokens[0]);
				if (customer == null) {
					customer = new Customer();
					customer.setCustomerId(tokens[0]);
					em.persist(customer);
					em.flush();
				}
				customer.setTitle(tokens[1]);
				customer.setLastName(tokens[2]);
				customer.setFirstName(tokens[3]);
				customer.setUserName(tokens[5]);
				customer.setBirthDate(dateFormat.parse(tokens[4]));
				// birth date
				customer.setEmail(tokens[5]);
				customer.setCity(tokens[6]);
				customer.setStreet(tokens[7]);
				customer.setZipCode(tokens[8]);
				customer.setJob(tokens[9]);
				customer.setBalance(new BigDecimal(tokens[10]));
				customer.setBonus(Integer.valueOf(tokens[11]));
				customer.setBranchCode(tokens[12]);
				customer.setBankAccountant(bankAccountant);

				em.merge(customer);
			}
		}
	}

	private Customer getCustomer(String customerId) {
		try {
			return em
					.createNamedQuery("Customer.getCustomerByCustomerId",
							Customer.class).setParameter("id", customerId)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	private BankAccountant getAccountant(String accountantId) {
		try {
			return em
					.createNamedQuery("BankAccountant.getByAccountantId",
							BankAccountant.class)
					.setParameter("id", accountantId).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	private String[] getTokens(String line) {
		String[] array = line.split(";", -1);
		String[] tokens = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			tokens[i] = removeQuotationMarks(array[i]);
		}
		return tokens;
	}

	private String removeQuotationMarks(String in) {
		return in.replace("\"", "");
	}

	private String removeUTF8BOM(String s) {
		if (s.startsWith(UTF8_BOM)) {
			s = s.substring(1);
		}
		return s;
	}
}