package brian.example.boot.batch.example2.writer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import brian.example.boot.batch.example2.model.Ex2;

@Component("ex2Writer")
public class Writer implements ItemWriter<Ex2>{
	
	@Autowired
	@Qualifier("pgDatasource")
	DataSource dataSource;

	@Override
	@Transactional
	public void write(List<? extends Ex2> items) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement("INSERT INTO PEOPLE(FIRST_NAME, LAST_NAME, LAST_UPDATED) VALUES(?, ?, now() )");
			
			for(Ex2 ex2 : items) {
				pstmt.setString(1, ex2.getFirstName());
				pstmt.setString(2, ex2.getLastName());
				
				pstmt.executeUpdate();
			}
			
			con.commit();
			
		} catch (Exception e) {
			if(con != null) con.rollback();
		} finally {
			try{ 
				if(con != null ) con.setAutoCommit(true);
				if(pstmt != null ) pstmt.close();
				if(con != null ) con.close();
				
			} catch(Exception e) { // Do nothing 
			}
		}
		
	}
}
