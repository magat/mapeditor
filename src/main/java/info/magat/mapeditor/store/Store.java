package info.magat.mapeditor.store;

import info.magat.mapeditor.Map;
import info.magat.mapeditor.color.Color;
import info.magat.mapeditor.drawable.Position;
import info.magat.mapeditor.ui.ColorChangeEvent;
import info.magat.mapeditor.ui.Event;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Store {

    public List<Event> read(Map map, String data) {
        ArrayList<Event> events = new ArrayList<>();
        JSONObject json = new JSONObject(data);


        json.getJSONArray("events").forEach(e -> {
            Color color = Color.BLACK;
            ColorChangeEvent event = new ColorChangeEvent(color, new Position(0, 0));
            events.add(event);
        });
        return events;
    }

    public String writeEvent(Event event) {
        if(ColorChangeEvent.class.isAssignableFrom(event.getClass())){
            ColorChangeEvent colorChangeEvent = (ColorChangeEvent) event;


        }

        return "";
    }

    public Event readEvent(String representation) {
        return null;
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
