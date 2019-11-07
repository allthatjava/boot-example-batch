package brian.example.boot.batch.example3.etl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import brian.example.boot.batch.example3.model.Ex3;

@Component("ex3Reader")
public class Ex3Reader implements ItemReader<JdbcCursorItemReader<Ex3>>{
	
	@Autowired
	@Qualifier("pgDatasource")
	DataSource dataSource;

	@Override
	public JdbcCursorItemReader<Ex3> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		JdbcCursorItemReader<Ex3> reader = new JdbcCursorItemReader<>();
		reader.setDataSource(dataSource);
		reader.setSql("SELECT * FROM movie");
		reader.setRowMapper(new ExRowMapper());
		
		return reader;
	}
}

class ExRowMapper implements RowMapper<Ex3>{

	@Override
	public Ex3 mapRow(ResultSet rs, int rowNum) throws SQLException {
		Ex3 ex = new Ex3();
		ex.setMovieId(rs.getInt("movie_id"));
		ex.setTitle(rs.getString("title"));
		ex.setGenre(rs.getString("genre"));
		ex.setYear(rs.getInt("year"));

		return ex;
	}
}