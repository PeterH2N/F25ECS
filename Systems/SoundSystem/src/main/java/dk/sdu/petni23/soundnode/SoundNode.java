package dk.sdu.petni23.soundnode;


import dk.sdu.petni23.common.components.sound.SoundComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;

public class SoundNode extends Node {
    public SoundComponent soundComponent;

    public SoundNode(Entity entity) {
        super(entity);
    }

    @Override
    public void onRemove() {

    }
}
