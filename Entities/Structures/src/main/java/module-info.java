
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.structures.archerTower.ArcherTower;
import dk.sdu.petni23.structures.mine.Mine;
import dk.sdu.petni23.structures.nexus.Nexus;
import dk.sdu.petni23.structures.tower.HousePlugin;
import dk.sdu.petni23.structures.walls.StoneWall;
import dk.sdu.petni23.structures.mine.MinePlugin;
import dk.sdu.petni23.structures.tree.TreeSPI;

module Structures {
    requires javafx.graphics;
    requires GameEngine;
    requires Common;
    provides IPluginService with HousePlugin, Nexus,MinePlugin;
    provides IEntitySPI with TreeSPI, StoneWall, ArcherTower, Nexus, Mine;
}
