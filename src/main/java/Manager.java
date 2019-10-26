/**
 * Interface for classes that manage heart cards
 */
public interface Manager {

    /**
     * initialize manager
     * @param numberOfElements number of cards
     */
    void manage(int numberOfElements);

    void addCards(int number);

    int getCard(int index);

    int getFirstCard();

    int getLastCard();

}
