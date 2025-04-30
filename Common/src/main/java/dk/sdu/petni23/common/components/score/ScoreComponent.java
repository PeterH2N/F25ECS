package dk.sdu.petni23.common.components.score;

import dk.sdu.petni23.gameengine.Component;

public class ScoreComponent extends Component {
    public final int scoreOnDeath;

    public ScoreComponent(int scoreOnDeath) {
        this.scoreOnDeath = scoreOnDeath;
    }
}
