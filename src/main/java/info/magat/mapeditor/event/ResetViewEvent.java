package info.magat.mapeditor.event;

import info.magat.mapeditor.State;

public class ResetViewEvent implements Event {
    @Override
    public boolean apply(State state) {
        state.setCurrentGrid(state.getRoot());
        return true;
    }
}
