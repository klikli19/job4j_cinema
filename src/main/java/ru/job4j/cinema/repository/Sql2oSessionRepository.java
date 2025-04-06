package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.dto.SessionDto;
import ru.job4j.cinema.model.Session;


import java.util.Collection;

@Repository
public class Sql2oSessionRepository implements SessionRepository {

    private final Sql2o sql2o;

    public Sql2oSessionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public Collection<SessionDto> getAllSessions() {
        try (var connection = sql2o.open()) {

            var query = connection.createQuery(
                    """
                            SELECT
                            fs.id,
                            f.name,
                            start_time,
                            end_time,
                            h.name AS hall_name,
                            h.id as halls_id,
                            price
                            FROM sessions AS fs
                            INNER JOIN films AS f ON fs.film_id = f.id
                            INNER JOIN halls AS h ON fs.halls_id = h.id
                            ORDER BY start_time;
                            """);

            return query.setColumnMappings(SessionDto.COLUMN_MAPPING)
                    .executeAndFetch(SessionDto.class);
        }
    }

    public SessionDto getSessionById(int id) {
        try (var connection = sql2o.open()) {

            var query = connection.createQuery(
                    """
                            SELECT
                            fs.id,
                            f.id as film_id,
                            f.name,
                            fs.start_time,
                            fs.end_time,
                            h.name as hall_name,
                            fs.price,
                            fs.halls_id as halls_id
                            FROM sessions AS fs
                            INNER JOIN films AS f ON fs.film_id = f.id
                            INNER JOIN halls AS h ON fs.halls_id = h.id
                            where fs.id = :id
                            """);

            query.addParameter("id", id);

            return query.setColumnMappings(SessionDto.COLUMN_MAPPING)
                    .executeAndFetchFirst(SessionDto.class);
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {

            var query = connection.createQuery("""
                    DELETE
                    FROM sessions
                    WHERE id = :id
                    """);
            query.addParameter("id", id);

            query.executeUpdate();

            return connection.getResult() != 0;
        }
    }

    @Override
    public Session save(Session session) {
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO film_sessions
                    (film_id,
                    halls_id,
                    start_time,
                    end_time,
                    price)
                    values
                    (:filmId,
                    :hallsId,
                    :startTime,
                    :endTime,
                    :price)
                    """;

            var query = connection.createQuery(sql, true)
                    .addParameter("filmId", session.getFilmId())
                    .addParameter("hallsId", session.getHallsId())
                    .addParameter("startTime", session.getStartTime())
                    .addParameter("endTime", session.getEndTime())
                    .addParameter("price", session.getPrice());

            int generatedId = query.executeUpdate().getKey(Integer.class);

            session.setId(generatedId);

            return session;
        }
    }
}

