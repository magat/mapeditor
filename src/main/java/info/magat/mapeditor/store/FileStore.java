package info.magat.mapeditor.store;

import info.magat.mapeditor.ui.Event;
import info.magat.mapeditor.ui.History;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class FileStore {

    private final Store store = new Store();
    private final Path path = Paths.get("first.map");

    public void storeHistory(History history) throws IOException {
        System.out.println("Saving to file " + path.toString());

        Files.write(path, Arrays.asList(store.writeHeader(history.past.size())), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        Files.write(path, asStrings(history.past), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        Files.write(path, asStrings(history.future), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
    }

    public History readHistory() throws IOException {
        List<String> lines = Files.readAllLines(path);

        int currentEvent = store.readCurrentEvent(lines.remove(0));
        List<Event> events = lines.stream().map(store::readEvent).collect(toList());

        History history = new History();
        history.past.addAll(events.subList(0, currentEvent));
        history.future.addAll(events.subList(currentEvent, events.size()));

        return history;
    }

    private List<String> asStrings(List<Event> past) {
        return past.stream().map(store::writeEvent).collect(toList());
    }
}
