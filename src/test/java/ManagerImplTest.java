import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ManagerImplTest {

    private Manager manager;

    @Before
    public void initManager(){
        manager = ManagerImpl.newInstance();
    }

    @Test
    public void theManagerCanBeSetCorrectly(){
        manager.manage(4);
        try {
            manager.getCard(3);
            assertTrue(true);
        } catch(Exception e){
            fail("The manager has not to raise an exception");
        }
    }

    @Test
    public void theManagerCannotContainAHighNumberOfCards(){
        try {
            manager.manage(200000000);
            fail("The manager has to raise an exception");
        } catch(Exception e){
            assertTrue(true);
        }
    }




}
