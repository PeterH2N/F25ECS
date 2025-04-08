
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.structures.nexus.NexusPlugin;
import dk.sdu.petni23.structures.shop.ShopPlugin;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.structures.archerTower.ArcherTowerPlugin;
import dk.sdu.petni23.structures.tower.HousePlugin;
import dk.sdu.petni23.structures.walls.StoneWall;
import dk.sdu.petni23.structures.walls.WallPlugin;
import dk.sdu.petni23.structures.walls.WoodenWall;
import dk.sdu.petni23.structures.mine.MinePlugin;
import dk.sdu.petni23.structures.walls.StoneWall;
import dk.sdu.petni23.structures.walls.WallPlugin;
import dk.sdu.petni23.structures.walls.WoodenWall;
import dk.sdu.petni23.structures.tree.TreeSPI;

module Structures {
    requires javafx.graphics;
    requires GameEngine;
    requires Common;
    provides IPluginService with WallPlugin, HousePlugin, ArcherTowerPlugin, NexusPlugin,MinePlugin,ShopPlugin;
    provides IEntitySPI with TreeSPI,StoneWall;
    exports dk.sdu.petni23.structures.wall;
}
