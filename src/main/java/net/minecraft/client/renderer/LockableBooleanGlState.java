package net.minecraft.client.renderer;

public class LockableBooleanGlState extends GlStateManager.BooleanState {

    private State state = State.NONE;

    public LockableBooleanGlState(int capability) {
        super(capability);
    }

    @Override
    public void setDisabled() {
        if (state == State.NONE)
            super.setDisabled();
    }

    @Override
    public void setEnabled() {
        if (state == State.NONE)
            super.setEnabled();
    }

    public void setLockedState(State state) {
        this.state = state;
        switch (state) {
            case ENABLED:
                super.setEnabled();
            case DISABLED:
                super.setDisabled();
        }
    }

    public enum State {
        ENABLED, DISABLED, NONE
    }

}
