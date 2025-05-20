package dk.sdu.petni23.gameengine.services;

public abstract class ISystem
{
    private long avgDuration = 0;
    public int iterations = 0;
    public abstract void update(double deltaTime);
    public abstract int getPriority();

    public long getAvgDuration() {
        return avgDuration;
    }

    public void setAvgDuration(long avgDuration) {
        this.avgDuration = avgDuration;
    }

    protected enum Priority {
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
