package dev.makos.loans.service;

import dev.makos.loans.domain.dto.LoanDto;

public interface LoanService {

    /**
     *
     * @param mobileNumber - Mobile Number of the Customer
     */
    void create(String mobileNumber);

    /**
     *
     * @param mobileNumber - Input mobile Number
     *  @return Loan Details based on a given mobileNumber
     */
    LoanDto fetch(String mobileNumber);

    /**
     *
     * @param loansDto - LoansDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    boolean update(LoanDto loansDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of loan details is successful or not
     */
    boolean delete(String mobileNumber);

}
