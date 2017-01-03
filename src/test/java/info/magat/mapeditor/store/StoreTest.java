package info.magat.mapeditor.store;

import info.magat.mapeditor.color.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StoreTest {

    private Store store = new Store();

    @Test
    public void colorSerialization(){
        assertEquals(Color.RED, store.readColor("#ff0000"));
        assertEquals(Color.BLACK, store.readColor("#000000"));
        assertEquals(Color.WHITE, store.readColor("#ffffff"));

        assertEquals("#ff0000", store.writeColor(Color.RED));
        assertEquals("#000000", store.writeColor(Color.BLACK));
        assertEquals("#ffffff", store.writeColor(Color.WHITE));

        Color color = Color.random();
        assertEquals(color, store.readColor(store.writeColor(color)));
    }

    @Test
    public void eventSerialization(){
//        Event event = store.readEvent("COLOR_CHANGE:#ff0000:0,0");
//        assertEquals(new ColorChangeEvent(Color.RED, new Position(0,0)), event);
//        assertEquals("COLOR_CHANGE:#ff0000:0,0", store.writeEvent(event));
//        assertEquals(event, store.readEvent(store.writeEvent(event)));
    }

}
