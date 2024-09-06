package dev.makos.cards.mapper;

import dev.makos.cards.domain.dto.CardDto;
import dev.makos.cards.domain.entity.Card;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CardMapper {

    public static CardDto mapToCardDto(Card card, CardDto cardDto) {
        cardDto.setCardNumber(card.getCardNumber());
        cardDto.setCardType(card.getCardType());
        cardDto.setMobileNumber(card.getMobileNumber());
        cardDto.setTotalLimit(card.getTotalLimit());
        cardDto.setAvailableAmount(card.getAvailableAmount());
        cardDto.setAmountUsed(card.getAmountUsed());
                return cardDto;
    }

    public static Card mapToCard(CardDto cardDto, Card card) {
        card.setCardNumber(cardDto.getCardNumber());
        card.setCardType(cardDto.getCardType());
        card.setMobileNumber(cardDto.getMobileNumber());
        card.setTotalLimit(cardDto.getTotalLimit());
        card.setAvailableAmount(cardDto.getAvailableAmount());
        card.setAmountUsed(cardDto.getAmountUsed());
        return card;
    }
}
