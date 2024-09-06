package dev.makos.cards.service.impl;

import dev.makos.cards.constants.CardConstants;
import dev.makos.cards.domain.dto.CardDto;
import dev.makos.cards.domain.entity.Card;
import dev.makos.cards.exception.CardAlreadyExistsException;
import dev.makos.cards.exception.ResourceNotFoundException;
import dev.makos.cards.mapper.CardMapper;
import dev.makos.cards.repository.CardRepository;
import dev.makos.cards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class CardServiceImpl implements CardService {

    private static final Random RANDOM = new Random();

    private final CardRepository cardRepository;

    @Override
    public void create(String mobileNumber) {
        Optional<Card> optionalCards = cardRepository.findByMobileNumber(mobileNumber);
        if (optionalCards.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + mobileNumber);
        }
        cardRepository.save(createNewCard(mobileNumber));
    }

    private Card createNewCard(String mobileNumber) {
        Card newCard = new Card();
        long randomCardNumber = 100000000000L + RANDOM.nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    @Override
    public CardDto fetch(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
        return CardMapper.mapToCardDto(card, new CardDto());
    }

    @Override
    public boolean update(CardDto cardsDto) {
        Card card = cardRepository.findByCardNumber(cardsDto.getCardNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
        CardMapper.mapToCard(cardsDto, card);
        cardRepository.save(card);
        return true;
    }

    @Override
    public boolean delete(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
        cardRepository.deleteById(card.getCardId());
        return true;
    }
}
