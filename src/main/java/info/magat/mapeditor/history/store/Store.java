package info.magat.mapeditor.history.store;

import info.magat.mapeditor.color.Color;
import info.magat.mapeditor.drawable.Position;
import info.magat.mapeditor.event.ColorChangeEvent;
import info.magat.mapeditor.event.Event;
import info.magat.mapeditor.event.ResetViewEvent;
import info.magat.mapeditor.event.SplitCellEvent;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Store {

    private static final String CURRENT_EVENT = "CURRENT";
    private static final String COLOR_CHANGE = "COLOR_CHANGE";
    private static final String SPLIT = "SPLIT";
    private static final String RESET_VIEW = "RESET_VIEW";
    private static final String EVENT_DATA_SEPARATOR = ":";
    private static final String POSITION_DATA_SEPARATOR = ",";

    public String writeHeader(int currentEventPosition) {
        return CURRENT_EVENT + EVENT_DATA_SEPARATOR + currentEventPosition;
    }

    public int readCurrentEvent(String header) {
        return Integer.parseInt(header.substring(CURRENT_EVENT.length() + EVENT_DATA_SEPARATOR.length()));
    }

    public String writeEvent(Event event) {
        if (ColorChangeEvent.class.isAssignableFrom(event.getClass())) {
            ColorChangeEvent ccEvent = (ColorChangeEvent) event;
            return Stream.of(COLOR_CHANGE, writeColor(ccEvent.color), writePosition(ccEvent.position)).collect(Collectors.joining(EVENT_DATA_SEPARATOR));
        }

        if (SplitCellEvent.class.isAssignableFrom(event.getClass())) {
            SplitCellEvent sEvent = (SplitCellEvent) event;
            return Stream.of(SPLIT, writePosition(sEvent.position)).collect(Collectors.joining(EVENT_DATA_SEPARATOR));
        }

        if(ResetViewEvent.class.isAssignableFrom(event.getClass())){
            return RESET_VIEW;
        }

        return "";
    }

    public Event readEvent(String representation) {
        String[] elements = representation.split(EVENT_DATA_SEPARATOR);

        if (elements[0].equals(COLOR_CHANGE)) {
            return new ColorChangeEvent(readColor(elements[1]), readPosition(elements[2]));
        }

        if (elements[0].equals(SPLIT)) {
            return new SplitCellEvent(readPosition(elements[1]));
        }

        if (elements[0].equals(RESET_VIEW)) {
            return new ResetViewEvent();
        }

        return null;
    }

    public Position readPosition(String representation) {
        String[] elements = representation.split(POSITION_DATA_SEPARATOR);
        return new Position(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]));
    }

    public String writePosition(Position position) {
        return position.x + POSITION_DATA_SEPARATOR + position.y;
    }


    public String writeColor(Color color) {
        return "#" + toHex(color.red) + toHex(color.green) + toHex(color.blue);
    }

    private String toHex(float red) {
        String s = Integer.toHexString(Math.round(red));
        return s.length() == 2 ? s : "0" + s;
    }

    public Color readColor(String representation) {
        return new Color(
                Integer.valueOf(representation.substring(1, 3), 16),
                Integer.valueOf(representation.substring(3, 5), 16),
                Integer.valueOf(representation.substring(5, 7), 16)
        );
    }


}
