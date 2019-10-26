import java.util.*;
import java.util.stream.IntStream;

public class ManagerImpl implements Manager {

    private class Card {
        private final long id = new Random().nextLong();
    }

    private static final int MAX_LENGTH = 3000;

    public static Manager newInstance() {
        return new ManagerImpl();
    }

    private final List<Card> array = new ArrayList<>();

    private ManagerImpl(){}

    @Override
    public void manage(int numberOfElements) {
        if(numberOfElements >= MAX_LENGTH){
            throw new IllegalArgumentException("Number is too high");
        }
        IntStream.range(0, numberOfElements)
                .boxed().map(elem -> new Card()).forEach(array::add);
    }

    @Override
    public void addCards(int number) {
        IntStream.range(0, number)
                .boxed().map(elem -> new Card()).forEach(array::add);
    }

    @Override
    public int getCard(int index) {
        return (int)array.get(index).id;
    }

    @Override
    public int getFirstCard() {
        return (int)array.get(0).id;
    }

    @Override
    public int getLastCard() {
        return (int)array.get(array.size() - 1).id;
    }
}
