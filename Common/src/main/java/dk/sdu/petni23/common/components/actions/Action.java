package dk.sdu.petni23.common.components.actions;

import dk.sdu.petni23.common.components.Dispatch;

public class Action
{
    public Dispatch onDispatch;
    public long delay = 0;
    public long duration = 0;
    public int animationIndex = 0;
    public boolean biDirectional = false;
    public double strength = 1;
}

