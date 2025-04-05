
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.structures.nexus.NexusPlugin;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.structures.archerTower.ArcherTowerPlugin;
import dk.sdu.petni23.structures.tower.HousePlugin;
import dk.sdu.petni23.structures.tree.TreePlugin;
import dk.sdu.petni23.structures.wall.WallPlugin;

module Structures {
    requires javafx.graphics;
    requires GameEngine;
    requires Common;
    provides IPluginService with TreePlugin, WallPlugin, HousePlugin, ArcherTowerPlugin, NexusPlugin;
    exports dk.sdu.petni23.structures.wall;
}
