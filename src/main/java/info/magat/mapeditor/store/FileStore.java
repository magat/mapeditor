package info.magat.mapeditor.store;

import info.magat.mapeditor.event.Event;
import info.magat.mapeditor.event.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Component
public class FileStore {

    private final Store store;
    private final Path path = Paths.get("first.map");

    public FileStore(@Autowired  Store store) {
        this.store = store;
    }

    public void storeHistory(History history) throws IOException {
        System.out.println("Saving to file " + path.toString());

        Files.write(path, Arrays.asList(store.writeHeader(history.pastSize() )), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        Files.write(path, asStrings(history.past()), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        Files.write(path, asStrings(history.future()), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
    }

    public History readHistory() throws IOException {
        List<String> lines = Files.readAllLines(path);

        int currentEvent = store.readCurrentEvent(lines.remove(0));
        List<Event> events = lines.stream().map(store::readEvent).filter(Objects::nonNull).collect(toList());

        History history = new History(events.subList(0, currentEvent), events.subList(currentEvent, events.size()));

        return history;
    }

    private List<String> asStrings(Stream<Event> past) {
        return past.map(store::writeEvent).collect(toList());
    }
}
