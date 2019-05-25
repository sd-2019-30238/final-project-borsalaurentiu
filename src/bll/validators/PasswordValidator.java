package bll.validators;

import bll.UserBLL;
import model.User;

public class PasswordValidator implements Validator<User> {
	
	public void validate(User user) {
		UserBLL userBLL = new UserBLL();
		if (!(userBLL.findPassword(user)).equals(user.getPassword())) {
			throw new IllegalArgumentException("Wrong password!");
		}
	}

}
