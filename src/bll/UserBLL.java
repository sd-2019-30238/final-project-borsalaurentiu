package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.PasswordValidator;
import bll.validators.Validator;
import dao.UserDAO;
import model.User;

public class UserBLL {
	private List<Validator<User>> loginValidator;

	public UserBLL() {
		loginValidator = new ArrayList<Validator<User>>();
		loginValidator.add(new PasswordValidator());
	}

	public String findPassword(User user) {
		String password = UserDAO.findPassword(user);
		if (password == null) {
			throw new NoSuchElementException("The user with name =" + user.getUsername() + " was not found!");
		}
		return password;
	}

	public boolean isUser(User user) {
		for (Validator<User> v : loginValidator) {
			v.validate(user);
		}
		return true;
	}

	public void insertUser(User user) {
		UserDAO.insertUser(user);
	}
}
