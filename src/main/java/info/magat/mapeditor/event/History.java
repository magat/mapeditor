package info.magat.mapeditor.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class History {

    private final List<Event> past;
    private final List<Event> future;

    public History() {
        past = new ArrayList<>();
        future = new ArrayList<>();
    }

    public History(List<Event> past, List<Event> future) {
        this.past = new ArrayList<>(past);
        this.future = new ArrayList<>(future);
    }

    public Optional<Event> next() {
        if (future.isEmpty()) {
            return Optional.empty();
        }

        Event event = future.remove(future.size() - 1);
        past.add(event);
        return Optional.of(event);
    }

    public void previous() {
        if (!past.isEmpty()) {
            future.add(past.remove(past.size() - 1));
        }
    }

    public void add(Event e){
        past.add(e);
    }

    public Stream<Event> past() {
        return past.stream();
    }

    public Stream<Event> future() {
        return future.stream();
    }

    public int pastSize(){
        return past.size();
    }
}
