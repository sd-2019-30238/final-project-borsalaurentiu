package bll.validators;

import bll.UserBLL;
import model.User;

public class AccountValidator implements Validator<User> {
	
	public void validate(User user) {
		UserBLL userBLL = new UserBLL();
		if (userBLL.findUserByName(user.getName()) != 0) {
			throw new IllegalArgumentException("This username is used!");
		}
	}
}
