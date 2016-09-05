package by.training.hrsystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.hrsystem.dao.VacancyDAO;
import by.training.hrsystem.dao.exception.DAOException;
import by.training.hrsystem.dao.exception.DAODataDoesNotExistException;
import by.training.hrsystem.dao.pool.ConnectionPool;
import by.training.hrsystem.dao.pool.exception.ConnectionPoolException;
import by.training.hrsystem.domain.Vacancy;
import by.training.hrsystem.domain.type.ActiveType;
import by.training.hrsystem.domain.type.CurrencyType;
import by.training.hrsystem.domain.type.EmploymentType;
import by.training.hrsystem.domain.type.HotType;

public class DBVacancyDAO implements VacancyDAO {
	private static final Logger logger = LogManager.getRootLogger();
	private static final String SQL_ADD_VACANCY = "INSERT INTO vacancy (name, salary, currency, description, conditions, employment_type, email) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
	private static final String SQL_UPDATE_VACANCY = "UPDATE vacancy SET name=?, salary=?, currency=?, description=?, conditions=?, employment_type=? "
			+ "WHERE id_vacancy=?;";
	private static final String SQL_DELETE_VACANCY = "DELETE FROM vacancy WHERE id_vacancy=?;";
	private static final String SQL_ADD_TRANSLATION_VACANCY = "INSERT INTO tvacancy (id_vacancy, lang, name, description, conditions) VALUES (?, ?, ?, ?, ?);";
	private static final String SQL_UPDATE_TRANSLATION_VACANCY = "UPDATE tvacancy SET name=?, description=?, conditions=? WHERE id_vacancy=? and lang=?;";
	private static final String SQL_DELETE_TRANSLATION_VACANCY = "DELETE FROM tvacancy WHERE id_vacancy=? and lang=?;";
	private static final String SQL_SELECT_COUNT_VACANCY = "SELECT count(id_vacancy) as vacancy_count FROM vacancy;";
	private static final String SQL_SELECT_VACANCY_BY_ID = "SELECT * FROM vacancy WHERE id_vacancy=?;";
	private static final String SQL_SELECT_TRANSLATE_VACANCY_BY_ID = "SELECT v.id_vacancy, coalesce(tv.name, v.name) AS name, "
			+ "v.salary, v.currency, v. publish_date, coalesce(tv.description, v.description) AS description, "
			+ "coalesce(tv.conditions, v.conditions) AS conditions, v.employment_type, v.active, v.hot, v.email "
			+ "FROM vacancy AS v LEFT JOIN (SELECT * FROM tvacancy WHERE lang = ?) AS tv USING(id_vacancy)  WHERE id_vacancy=?;";
	private static final String SQL_SELECT_ALL_VACANCY = "SELECT * FROM vacancy";
	private static final String SQL_SELECT_ALL_TRANSL_VACANCY = "SELECT v.id_vacancy, coalesce(tv.name, v.name) AS name, "
			+ "v.salary, v.currency, v. publish_date, coalesce(tv.description, v.description) AS description, "
			+ "coalesce(tv.conditions, v.conditions) AS conditions, v.employment_type, v.active, v.email "
			+ "FROM vacancy AS v LEFT JOIN (SELECT * FROM tvacancy WHERE lang = ?) AS tv USING(id_vacancy);";
	private static final String SQL_SELECT_VACANCY_BY_HREMAIL = "SELECT * FROM vacancy WHERE email=? LIMIT ?,?;";
	private static final String SQL_SELECT_TRANSL_VAC_BY_HREMAIL = "SELECT v.id_vacancy, coalesce(tv.name, v.name) AS name, "
			+ "v.salary, v.currency, v. publish_date, coalesce(tv.description, v.description) AS description, "
			+ "coalesce(tv.conditions, v.conditions) AS conditions, v.employment_type, v.active, v.hot, v.email "
			+ "FROM vacancy AS v LEFT JOIN (SELECT * FROM tvacancy WHERE lang = ?) AS tv USING(id_vacancy)  WHERE v.email=? LIMIT ?,?;";
	private static final String SQL_SELECT_COUNT_VACANCY_BY_HR_EMAIL = "SELECT count(id_vacancy) as vacancy_count FROM vacancy WHERE email=?;";
	private static final String SQL_SELECT_VACANCY_LIKE = "SELECT * FROM vacancy WHERE name LIKE ?;";
	// private static final String SQL_SELECT_TRASL_VACANCY_LIKE = "?";
	private static final String SQL_COUNT_ALL_ACTIVE_VACANCY = "SELECT count(id_vacancy) as countResume FROM vacancy WHERE active='active';";
	private static final String SQL_SELECT_ALL_ACTIVE_VACANCY = "SELECT * FROM vacancy WHERE active='active' LIMIT ?,?;";
	private static final String SQL_SELECT_ALL_TRANSL_ACTIVE_VACANCY = "SELECT v.id_vacancy, coalesce(tv.name, v.name) AS name, "
			+ "v.salary, v.currency, v. publish_date, coalesce(tv.description, v.description) AS description, "
			+ "coalesce(tv.conditions, v.conditions) AS conditions, v.employment_type, v.active, v.hot, v.email "
			+ "FROM vacancy AS v LEFT JOIN (SELECT * FROM tvacancy WHERE lang = ?) AS tv USING(id_vacancy)  WHERE active='active' LIMIT ?, ?;";
	private static final String SQL_SELECT_ALL_HOT_VACANCY = "SELECT * FROM vacancy WHERE hot='hot' LIMIT 7;";
	private static final String SQL_SELECT_ALL_TRANSL_HOT_VACANCY = "SELECT v.id_vacancy, coalesce(tv.name, v.name) AS name, "
			+ "v.salary, v.currency, v. publish_date, coalesce(tv.description, v.description) AS description, "
			+ "coalesce(tv.conditions, v.conditions) AS conditions, v.employment_type, v.active, v.hot, v.email "
			+ "FROM vacancy AS v LEFT JOIN (SELECT * FROM tvacancy WHERE lang = ?) AS tv USING(id_vacancy)  WHERE hot='hot' LIMIT 7;";
	private static final String SQL_ACTIVATE_VACANCY = "UPDATE vacancy SET active='active', publish_date=? WHERE id_vacancy=?;";
	private static final String SQL_HOT_VACANCY = "UPDATE vacancy SET hot='hot' WHERE id_vacancy=?;";
	private static final String SQL_DEACTIVATE_VACANCY = "UPDATE vacancy SET active='non active', hot='non hot' WHERE id_vacancy=?;";

	@Override
	public void addVacancy(Vacancy vacancy) throws DAOException {
		Connection conn = null;
		PreparedStatement ps = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			conn = pool.takeConnection();
			ps = conn.prepareStatement(SQL_ADD_VACANCY);
			ps.setString(1, vacancy.getName());
			ps.setInt(2, vacancy.getSalary());
			ps.setString(3, vacancy.getCurrency().getCurrencyType());
			ps.setString(4, vacancy.getDescription());
			ps.setString(5, vacancy.getCondition());
			ps.setString(6, vacancy.getEmploymentType().getCurrencyType());
			ps.setString(7, vacancy.getHrEmail());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Faild create Vacancy: ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool problems!", e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(conn);
				ps.close();
			} catch (SQLException | ConnectionPoolException e) {
				logger.error("Faild to close connection or ps", e);
			}
		}

	}

	@Override
	public void updateVacancy(Vacancy vacancy) throws DAOException {
		Connection conn = null;
		PreparedStatement ps = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			conn = pool.takeConnection();
			ps = conn.prepareStatement(SQL_UPDATE_VACANCY);
			ps.setString(1, vacancy.getName());
			ps.setInt(2, vacancy.getSalary());
			ps.setString(3, vacancy.getCurrency().getCurrencyType());
			ps.setString(5, vacancy.getDescription());
			ps.setString(6, vacancy.getCondition());
			ps.setString(7, vacancy.getEmploymentType().getCurrencyType());
			ps.setInt(8, vacancy.getIdVacancy());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Faild to update Vacancy: ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool problems!", e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(conn);
				ps.close();
			} catch (SQLException | ConnectionPoolException e) {
				logger.error("Faild to close connection or ps", e);
			}
		}

	}

	@Override
	public void deleteVacancy(int idVacancy) throws DAOException {
		Connection conn = null;
		PreparedStatement ps = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			conn = pool.takeConnection();
			ps = conn.prepareStatement(SQL_DELETE_VACANCY);
			ps.setInt(1, idVacancy);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Faild delete Vacancy: ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool problems!", e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(conn);
				ps.close();
			} catch (SQLException | ConnectionPoolException e) {
				logger.error("Faild to close connection or ps", e);
			}
		}

	}

	@Override
	public void addTranslateVacancy(Vacancy vacancy, String lang) throws DAOException {
		Connection conn = null;
		PreparedStatement ps = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			conn = pool.takeConnection();
			ps = conn.prepareStatement(SQL_ADD_TRANSLATION_VACANCY);
			ps.setInt(1, vacancy.getIdVacancy());
			ps.setString(2, lang);
			ps.setString(3, vacancy.getName());
			ps.setString(4, vacancy.getDescription());
			ps.setString(5, vacancy.getCondition());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Faild create translation of Vacancy: ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool problems!", e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(conn);
				ps.close();
			} catch (SQLException | ConnectionPoolException e) {
				logger.error("Faild to close connection or ps", e);
			}
		}

	}

	@Override
	public void updateTranslateVacancy(Vacancy vacancy, String lang) throws DAOException {
		Connection conn = null;
		PreparedStatement ps = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			conn = pool.takeConnection();
			ps = conn.prepareStatement(SQL_UPDATE_TRANSLATION_VACANCY);
			ps.setString(1, vacancy.getName());
			ps.setString(2, vacancy.getDescription());
			ps.setString(3, vacancy.getCondition());
			ps.setInt(4, vacancy.getIdVacancy());
			ps.setString(5, lang);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Faild update translation of Vacancy: ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool problems!", e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(conn);
				ps.close();
			} catch (SQLException | ConnectionPoolException e) {
				logger.error("Faild to close connection or ps", e);
			}
		}

	}

	@Override
	public void deleteTranslateVacancy(int idVacancy, String lang) throws DAOException {
		Connection conn = null;
		PreparedStatement ps = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			conn = pool.takeConnection();
			ps = conn.prepareStatement(SQL_DELETE_TRANSLATION_VACANCY);
			ps.setInt(1, idVacancy);
			ps.setString(2, lang);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Faild delete translation of Vacancy: ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool problems!", e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(conn);
				ps.close();
			} catch (SQLException | ConnectionPoolException e) {
				logger.error("Faild to close connection or ps", e);
			}
		}

	}

	@Override
	public int selectCountVacancy() throws DAOException, DAODataDoesNotExistException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool pool = null;
		int countVacancy = 0;
		try {
			pool = ConnectionPool.getInstance();
			conn = pool.takeConnection();
			ps = conn.prepareStatement(SQL_SELECT_COUNT_VACANCY);
			rs = ps.executeQuery();
			if (rs.next()) {
				countVacancy = rs.getInt(1);
			} else {
				throw new DAODataDoesNotExistException("Vacancy not found!");
			}
		} catch (SQLException e) {
			throw new DAOException("Faild to find count: ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool problems!", e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(conn);
				ps.close();
			} catch (SQLException | ConnectionPoolException e) {
				logger.error("Faild to close connection or ps", e);
			}
		}

		return countVacancy;

	}

	@Override
	public Vacancy selectVacancyById(int idVacancy, String lang) throws DAOException, DAODataDoesNotExistException {
		Vacancy vacancy = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			conn = pool.takeConnection();
			if (lang.equals(SQLField.DEFAULT_LANGUAGE)) {
				ps = conn.prepareStatement(SQL_SELECT_VACANCY_BY_ID);
				ps.setInt(1, idVacancy);
			} else {
				ps = conn.prepareStatement(SQL_SELECT_TRANSLATE_VACANCY_BY_ID);
				ps.setString(1, lang);
				ps.setInt(2, idVacancy);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				vacancy = getVacacnyFromResultSet(rs);
			} else {
				throw new DAODataDoesNotExistException("Vacancy not found!");
			}
		} catch (SQLException e) {
			throw new DAOException("Faild to find vacancy: ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool problems!", e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(conn);
				ps.close();
				rs.close();
			} catch (SQLException | ConnectionPoolException e) {
				logger.error("Faild to close connection or ps", e);
			}
		}
		return vacancy;

	}

	@Override
	public List<Vacancy> selectAllVacancy(String lang) throws DAOException {
		List<Vacancy> vacancy = new ArrayList<Vacancy>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			conn = pool.takeConnection();
			if (lang.equals(SQLField.DEFAULT_LANGUAGE)) {
				ps = conn.prepareStatement(SQL_SELECT_ALL_VACANCY);
			} else {
				ps = conn.prepareStatement(SQL_SELECT_ALL_TRANSL_VACANCY);
				ps.setString(1, lang);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				vacancy.add(getVacacnyFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOException("Faild to find resume: ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool problems!", e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(conn);
				ps.close();
				rs.close();
			} catch (SQLException | ConnectionPoolException e) {
				logger.error("Faild to close connection or ps", e);
			}
		}

		return vacancy;
	}

	@Override
	public List<Vacancy> selectVacancyByHrEmail(String hrEmail, String lang, int pageNum, int amountPerPage)
			throws DAOException {
		List<Vacancy> vacancy = new ArrayList<Vacancy>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			conn = pool.takeConnection();
			if (lang.equals(SQLField.DEFAULT_LANGUAGE)) {
				ps = conn.prepareStatement(SQL_SELECT_VACANCY_BY_HREMAIL);
				ps.setString(1, hrEmail);
				ps.setInt(2, pageNum);
				ps.setInt(3, amountPerPage);

			} else {
				ps = conn.prepareStatement(SQL_SELECT_TRANSL_VAC_BY_HREMAIL);
				ps.setString(1, lang);
				ps.setString(2, hrEmail);
				ps.setInt(3, pageNum);
				ps.setInt(4, amountPerPage);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				vacancy.add(getVacacnyFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOException("Faild to find resume: ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool problems!", e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(conn);
				ps.close();
				rs.close();
			} catch (SQLException | ConnectionPoolException e) {
				logger.error("Faild to close connection or ps", e);
			}
		}

		return vacancy;
	}

	@Override
	public int selectCountVacancyByHrEmail(String hrEmail) throws DAOException, DAODataDoesNotExistException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool pool = null;
		int countVacancy = 0;
		try {
			pool = ConnectionPool.getInstance();
			conn = pool.takeConnection();
			ps = conn.prepareStatement(SQL_SELECT_COUNT_VACANCY_BY_HR_EMAIL);
			ps.setString(1, hrEmail);
			rs = ps.executeQuery();
			if (rs.next()) {
				countVacancy = rs.getInt(1);
			} else {
				throw new DAODataDoesNotExistException("Vacancy not found!");
			}
		} catch (SQLException e) {
			throw new DAOException("Faild to find count: ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool problems!", e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(conn);
				ps.close();
			} catch (SQLException | ConnectionPoolException e) {
				logger.error("Faild to close connection or ps", e);
			}
		}
		return countVacancy;

	}

	@Override
	public List<Vacancy> selectVacancyLike(String name) throws DAOException {
		List<Vacancy> vacancy = new ArrayList<Vacancy>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			conn = pool.takeConnection();
			ps = conn.prepareStatement(SQL_SELECT_VACANCY_LIKE);
			ps.setString(1, name);

			rs = ps.executeQuery();
			while (rs.next()) {
				vacancy.add(getVacacnyFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOException("Faild to find resume: ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool problems!", e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(conn);
				ps.close();
				rs.close();
			} catch (SQLException | ConnectionPoolException e) {
				logger.error("Faild to close connection or ps", e);
			}
		}

		return vacancy;
	}

	@Override
	public List<Vacancy> selectAllActiveVacancy(String lang, int pageNum, int amountPerPage) throws DAOException {
		List<Vacancy> vacancyList = new ArrayList<Vacancy>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			conn = pool.takeConnection();
			if (lang.equals(SQLField.DEFAULT_LANGUAGE)) {
				ps = conn.prepareStatement(SQL_SELECT_ALL_ACTIVE_VACANCY);
				ps.setInt(1, pageNum);
				ps.setInt(2, amountPerPage);
			} else {
				ps = conn.prepareStatement(SQL_SELECT_ALL_TRANSL_ACTIVE_VACANCY);
				ps.setString(1, lang);
				ps.setInt(2, pageNum);
				ps.setInt(3, amountPerPage);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				vacancyList.add(getVacacnyFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOException("Faild to find resume: ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool problems!", e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(conn);
				ps.close();
				rs.close();
			} catch (SQLException | ConnectionPoolException e) {
				logger.error("Faild to close connection or ps", e);
			}
		}

		logger.debug("DBVacancyDAO.selectAllActiveResume() - pageNum={},amountPerPage={},vacancy = {}", pageNum,
				amountPerPage, vacancyList);
		return vacancyList;
	}

	@Override
	public int selectCountActiveVacancy() throws DAOException, DAODataDoesNotExistException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool pool = null;
		int countActiveVacancy = 0;
		try {
			pool = ConnectionPool.getInstance();
			conn = pool.takeConnection();
			ps = conn.prepareStatement(SQL_COUNT_ALL_ACTIVE_VACANCY);
			rs = ps.executeQuery();
			if (rs.next()) {
				countActiveVacancy = rs.getInt(1);
			} else {
				throw new DAODataDoesNotExistException("Vacancy not found!");
			}
		} catch (SQLException e) {
			throw new DAOException("Faild to find count: ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool problems!", e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(conn);
				ps.close();
			} catch (SQLException | ConnectionPoolException e) {
				logger.error("Faild to close connection or ps", e);
			}
		}

		logger.debug("DBVacancyDAO.selectCountAllActiveUser() - count = {}", countActiveVacancy);
		return countActiveVacancy;

	}

	@Override
	public void activateVacancy(int idVacancy) throws DAOException {
		Connection conn = null;
		PreparedStatement ps = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			conn = pool.takeConnection();
			ps = conn.prepareStatement(SQL_ACTIVATE_VACANCY);
			ps.setDate(1, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			ps.setInt(2, idVacancy);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Faild to activate vacancy: ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool problems!", e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(conn);
				ps.close();
			} catch (SQLException | ConnectionPoolException e) {
				logger.error("Faild to close connection or ps", e);
			}
		}

	}

	@Override
	public void deactivateVacancy(int idVacancy) throws DAOException {
		Connection conn = null;
		PreparedStatement ps = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			conn = pool.takeConnection();
			ps = conn.prepareStatement(SQL_DEACTIVATE_VACANCY);
			ps.setInt(1, idVacancy);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Faild to deactivate vacancy: ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool problems!", e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(conn);
				ps.close();
			} catch (SQLException | ConnectionPoolException e) {
				logger.error("Faild to close connection or ps", e);
			}
		}

	}

	@Override
	public void hotVacancy(int idVacancy) throws DAOException {
		Connection conn = null;
		PreparedStatement ps = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			conn = pool.takeConnection();
			ps = conn.prepareStatement(SQL_HOT_VACANCY);
			ps.setInt(1, idVacancy);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Faild to hot vacancy: ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool problems!", e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(conn);
				ps.close();
			} catch (SQLException | ConnectionPoolException e) {
				logger.error("Faild to close connection or ps", e);
			}
		}

	}

	@Override
	public List<Vacancy> selectAllHotVacancy(String lang) throws DAOException {
		List<Vacancy> vacancyList = new ArrayList<Vacancy>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			conn = pool.takeConnection();
			if (lang.equals(SQLField.DEFAULT_LANGUAGE)) {
				ps = conn.prepareStatement(SQL_SELECT_ALL_HOT_VACANCY);
			} else {
				ps = conn.prepareStatement(SQL_SELECT_ALL_TRANSL_HOT_VACANCY);
				ps.setString(1, lang);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				vacancyList.add(getVacacnyFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOException("Faild to find hot vacancy: ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool problems!", e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(conn);
				ps.close();
				rs.close();
			} catch (SQLException | ConnectionPoolException e) {
				logger.error("Faild to close connection or ps", e);
			}
		}

		logger.debug("DBVacancyDAO.selectAllHotResume() - vacancy = {}", vacancyList);
		return vacancyList;

	}

	private Vacancy getVacacnyFromResultSet(ResultSet set) throws SQLException {
		Vacancy vacancy = new Vacancy();
		vacancy.setIdVacancy(set.getInt(1));
		vacancy.setName(set.getString(2));
		vacancy.setSalary(set.getInt(3));
		vacancy.setCurrency(CurrencyType.valueOf(set.getString(4).toUpperCase()));
		vacancy.setPublishDate(set.getDate(5));
		vacancy.setDescription(set.getString(6));
		vacancy.setCondition(set.getString(7));
		vacancy.setEmploymentType(EmploymentType.valueOf(set.getString(8).toUpperCase().replace(' ', '_')));
		vacancy.setActive(ActiveType.valueOf(set.getString(9).toUpperCase().replace(' ', '_')));
		vacancy.setHotType(HotType.valueOf(set.getString(10).toUpperCase().replace(' ', '_')));
		vacancy.setHrEmail(set.getString(11));
		logger.debug("DBVacancyDAO.getVacancyFromResultSet() - vacancy = {}", vacancy);
		return vacancy;
	}

}
