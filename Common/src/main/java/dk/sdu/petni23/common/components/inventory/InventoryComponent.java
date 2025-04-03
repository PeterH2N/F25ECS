package dk.sdu.petni23.common.components.inventory;

import dk.sdu.petni23.gameengine.Component;

public class InventoryComponent extends Component {
    public boolean visible = false;
    private static final int gridSizeX = 4;
    private static final int gridSizeY = 4;

    // NEW: Inventory amounts
    public int meat = 0;
    public int wood = 0;
}
