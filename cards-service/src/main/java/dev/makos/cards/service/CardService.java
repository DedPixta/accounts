package dev.makos.cards.service;


import dev.makos.cards.domain.dto.CardDto;

public interface CardService {

    /**
     *
     * @param mobileNumber - Mobile Number of the Customer
     */
    void create(String mobileNumber);

    /**
     *
     * @param mobileNumber - Input mobile Number
     *  @return Card Details based on a given mobileNumber
     */
    CardDto fetch(String mobileNumber);

    /**
     *
     * @param cardsDto - CardsDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    boolean update(CardDto cardsDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of card details is successful or not
     */
    boolean delete(String mobileNumber);

}
