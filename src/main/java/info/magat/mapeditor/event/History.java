package info.magat.mapeditor.event;

import java.util.ArrayList;
import java.util.List;

public class History {

    public final List<Event> past;
    public final List<Event> future;

    public History() {
        past = new ArrayList<>();
        future = new ArrayList<>();
    }
}
