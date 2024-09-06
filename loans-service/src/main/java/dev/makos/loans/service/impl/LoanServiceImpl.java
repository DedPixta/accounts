package dev.makos.loans.service.impl;

import dev.makos.loans.constants.LoanConstants;
import dev.makos.loans.domain.dto.LoanDto;
import dev.makos.loans.domain.entity.Loan;
import dev.makos.loans.exception.LoanAlreadyExistsException;
import dev.makos.loans.exception.ResourceNotFoundException;
import dev.makos.loans.mapper.LoanMapper;
import dev.makos.loans.repository.LoanRepository;
import dev.makos.loans.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class LoanServiceImpl implements LoanService {

    private static final Random RANDOM = new Random();

    private final LoanRepository loanRepository;

    @Override
    public void create(String mobileNumber) {
        Optional<Loan> optionalLoan = loanRepository.findByMobileNumber(mobileNumber);
        if (optionalLoan.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " + mobileNumber);
        }
        loanRepository.save(createNewLoan(mobileNumber));
    }

    private Loan createNewLoan(String mobileNumber) {
        Loan newLoan = new Loan();
        long randomLoanNumber = 100000000000L + RANDOM.nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoanConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public LoanDto fetch(String mobileNumber) {
        Loan loan = loanRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
        return LoanMapper.mapToLoanDto(loan, new LoanDto());
    }

    @Override
    public boolean update(LoanDto loanDto) {
        Loan loan = loanRepository.findByLoanNumber(loanDto.getLoanNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "LoanNumber", loanDto.getLoanNumber()));
        LoanMapper.mapToLoan(loanDto, loan);
        loanRepository.save(loan);
        return true;
    }

    @Override
    public boolean delete(String mobileNumber) {
        Loan loan = loanRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
        loanRepository.deleteById(loan.getLoanId());
        return true;
    }
}
