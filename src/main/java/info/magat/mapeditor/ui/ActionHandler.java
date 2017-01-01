package info.magat.mapeditor.ui;

import info.magat.mapeditor.Mouse;
import info.magat.mapeditor.color.Color;
import info.magat.mapeditor.drawable.Cell;
import info.magat.mapeditor.drawable.Drawable;
import info.magat.mapeditor.drawable.Map;
import info.magat.mapeditor.drawable.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class ActionHandler {

    private static ActionHandler instance;
    private static ArrayList<Drawable> clickables = new ArrayList<>();
    private Color selectedColor = Color.BLACK;
    private List<Event> events = new ArrayList<>();
    private Map map;

    public ActionHandler(Map map) {
        this.map = map;
    }

    public void click() {
        clickables.stream().filter(Mouse::isOver).forEach(d -> {
            if (d instanceof Cell) {
                Cell cell = (Cell) d;

                if (d instanceof Toolbar.ToolBarCell) {
                    selectedColor = cell.getColor();
                    return;
                }

                throwEvent(new ColorChangeEvent(selectedColor, cell));
            }
        });
    }

    public static void registerClickAble(Drawable drawable) {
        clickables.add(drawable);
    }

    public static ActionHandler get() {
        return instance;
    }

    public void throwEvent(Event e) {
        if(e.apply()){
            events.add(e);
        }
    }

    public void back() {
        if (!events.isEmpty()) {
            originalState();
            events.remove(events.size() - 1);
            events.forEach(Event::apply);
        }
    }

    public static ActionHandler init(Map map) {
        if (instance == null) {
            instance = new ActionHandler(map);
        }
        return instance;
    }

    public void originalState() {
        map.cells().forEach(c -> c.setColor(Color.BLACK));
    }
}
