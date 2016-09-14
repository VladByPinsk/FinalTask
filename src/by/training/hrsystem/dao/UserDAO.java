package by.training.hrsystem.dao;

import by.training.hrsystem.dao.exception.DAOException;
import by.training.hrsystem.domain.User;

public interface UserDAO {
	void addUser(User user) throws DAOException;

	void addTranslateUser(User user, String lang) throws DAOException;

	void deleteUser(String email) throws DAOException;

	void deleteTranslateUser(String email, String lang) throws DAOException;

	void updateUser(User user) throws DAOException;

	void updateTranslateUser(User user, String lang) throws DAOException;

	User userAuthentication(String email, String password) throws DAOException;
	
	boolean userExist(String email, String password) throws DAOException;

	User getUserByEmail(String email) throws DAOException;

	User getUserByIdVacancy(int idVcancy) throws DAOException, DAOException;

	User getUserByIdResume(int idResume) throws DAOException, DAOException;

	int countAllApplicants() throws DAOException;
}