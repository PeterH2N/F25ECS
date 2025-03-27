
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.structures.tree.TreePlugin;
import dk.sdu.petni23.structures.wall.WallPlugin;

module Structures {
    requires javafx.graphics;
    requires GameEngine;
    requires Common;
    provides IPluginService with TreePlugin,WallPlugin;
}