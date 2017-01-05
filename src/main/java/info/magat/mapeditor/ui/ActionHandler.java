package info.magat.mapeditor.ui;

import info.magat.mapeditor.Map;
import info.magat.mapeditor.Mouse;
import info.magat.mapeditor.color.Color;
import info.magat.mapeditor.drawable.Cell;
import info.magat.mapeditor.drawable.Drawable;
import info.magat.mapeditor.drawable.Toolbar;
import info.magat.mapeditor.store.FileStore;

import java.io.IOException;
import java.util.ArrayList;

public class ActionHandler {

    private static ActionHandler instance;
    private static ArrayList<Drawable> clickables = new ArrayList<>();
    private Color selectedColor = Color.BLACK;
    private Map map;
    private History history = new History();
    private FileStore fileStore = new FileStore();

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

                throwEvent(new ColorChangeEvent(selectedColor, map.getGrid().positionOf(cell)));
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
        if(e.apply(map)){
            history.past.add(e);
        }
    }

    public void back() {
        if (!history.past.isEmpty()) {
            originalState();
            history.future.add(history.past.remove(history.past.size() - 1));
            history.past.forEach(e -> e.apply(map));
        }
    }

    public void forward() {
        if (!history.future.isEmpty()) {
            Event event = history.future.remove(history.future.size() - 1);
            event.apply(map);
            history.past.add(event);
        }
    }

    public static ActionHandler init(Map map) {
        if (instance == null) {
            instance = new ActionHandler(map);
        }
        return instance;
    }

    public void originalState() {
        map.getGrid().cells().forEach(c -> c.setColor(Color.BLACK));
    }

    public void save(){
        try {
            fileStore.storeHistory(history);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(){
        try {
            history = fileStore.readHistory();

            originalState();
            history.past.forEach(e -> e.apply(map));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
