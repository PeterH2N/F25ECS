package dk.sdu.petni23.gameengine.services;

public interface ISystem
{
    void update(double deltaTime);
    int getPriority();
    enum Priority {
        PREPROCESSING(0),
        PROCESSING(1),
        POSTPROCESSING(2);
        private final int index;
        public int get() {
            return index;
        }
        Priority(int index) {
            this.index = index;
        }
    }
}
