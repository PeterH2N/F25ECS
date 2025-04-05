
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.structures.tower.House;
import dk.sdu.petni23.structures.tower.HousePlugin;
import dk.sdu.petni23.structures.tree.TreePlugin;
import dk.sdu.petni23.structures.mine.MinePlugin;
import dk.sdu.petni23.structures.wall.WallPlugin;

module Structures {
    requires javafx.graphics;
    requires GameEngine;
    requires Common;
    provides IPluginService with TreePlugin,WallPlugin, HousePlugin, MinePlugin;
}