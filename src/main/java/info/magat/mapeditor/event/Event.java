package info.magat.mapeditor.event;

import info.magat.mapeditor.State;

public interface Event {

    boolean apply(State state);

}
