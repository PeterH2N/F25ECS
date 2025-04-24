package dk.sdu.petni23.common.components.actions;

import dk.sdu.petni23.common.components.Dispatch;

public class Action
{
    public transient Dispatch onDispatch = null;
    public long delay = 0;
    public long duration = 0;
    public int animationIndex = 0;
    public final Directionality directionality;
    public double strength = 1;

    public static Action DEFAULT = new Action(Directionality.BI);

    public Action(Directionality direction)
    {
        this.directionality = direction;
    }

    public enum Directionality {
        BI(2),
        QUAD(4),
        OCT(8);
        private final int i;

        Directionality(int i) {
            this.i = i;
        }

        public int value(){
            return i;
        }
    }
}

