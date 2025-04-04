
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.structures.nexus.NexusPlugin;
import dk.sdu.petni23.structures.tower.HousePlugin;
import dk.sdu.petni23.structures.tree.TreePlugin;
import dk.sdu.petni23.structures.wall.WallPlugin;

module Structures {
    requires javafx.graphics;
    requires GameEngine;
    requires Common;
    provides IPluginService with TreePlugin,WallPlugin, HousePlugin, NexusPlugin;
}