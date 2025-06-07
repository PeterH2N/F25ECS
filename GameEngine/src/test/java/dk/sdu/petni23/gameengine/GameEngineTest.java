package dk.sdu.petni23.gameengine;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI.Type;

public class GameEngineTest {

    private Entity testEntity;

    @BeforeEach
    public void setUp(){
        testEntity = new Entity(Type.ENEMY);
    }
    @AfterEach 
    public void tearDown(){
        testEntity = null;
    }
    @Test
    public void testAddEntity(){
        Engine.addEntity(testEntity);
        assertTrue(Engine.getEntities().contains(testEntity));
    }
    @Test
    public void removeEntity(){
        testEntity = new Entity(Type.ENEMY);
        Engine.removeEntity(testEntity);
        assertFalse(Engine.getEntities().contains(testEntity));
    }

}
