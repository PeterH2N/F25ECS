package dk.sdu.petni23.gameengine.services;

public interface IPhysicsSystem
{
    void step(double deltaTime);

    int getPriority();
    enum Priority {
        FIRST(0),
        SECOND(1);
        private final int index;
        public int get() {
            return index;
        }
        Priority(int index) {
            this.index = index;
        }
    }
}
