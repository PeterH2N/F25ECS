package dk.sdu.petni23.shopping;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.gameengine.entity.IEntitySPI.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class ShopTest {
    

    private ShopLogic shopLogic = new ShopLogic();

    /*@BeforeEach
    public void setUp(){
        Map<Type,Integer> testInventory = new HashMap<>();
        testInventory.put(Type.STONE,10);
        try (MockedStatic<GameData> mockGameData = mockStatic(GameData.class)){
            mockGameData.when(()->GameData.getPlayerInventory()).thenReturn(testInventory);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }*/

    @Test
    public void testCanAffordTrue(){
        Map<Type,Integer> testInventory = new HashMap<>();
        testInventory.put(Type.STONE,6);
        Map<Type,Integer> testPrices = new HashMap<>();
        testPrices.put(Type.STONE, 5);
        boolean canAfford = shopLogic.canAfford(testPrices, Optional.of(testInventory));
        assertTrue(canAfford);
    }
    @Test
    public void testCanAffordFalse(){
        Map<Type,Integer> testInventory = new HashMap<>();
        testInventory.put(Type.STONE,4);
        Map<Type,Integer> testPrices = new HashMap<>();
        testPrices.put(Type.STONE, 5);
        boolean canAfford = shopLogic.canAfford(testPrices, Optional.of(testInventory));
        assertFalse(canAfford);
    }
}
