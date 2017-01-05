package info.magat.mapeditor.event;

import info.magat.mapeditor.drawable.Grid;

public interface Event {

    boolean apply(Grid grid);

}
