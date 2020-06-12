package hasses.magical.tools.model;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.Length;

public class Userupdate implements Serializable{

	private static final Logger LOGGER =Logger.getLogger(Userupdate.class);
	
	private static final long serialVersionUID = 1L;

	@NotNull(message="Epost är obligatoriskt fällt")
	@Email(message="Felaktig epost adress") 
	private String email;

	public Userupdate(
			@NotNull(message = "Epost är obligatoriskt fällt") @Email(message = "Felaktig epost adress") String email,
			@NotNull(message = "Lösenord är obligatoriskt fällt") @Length(min = 6, message = "Lösenordet ska va minst 6 tecken") String password,
			@NotNull(message = "Lösenord är obligatoriskt fällt") @Length(min = 6, message = "Lösenordet ska va minst 6 tecken") String newPassword) {
		super();
		this.email = email;
		this.password = password;
		this.newPassword = newPassword;
	}
	
	public Userupdate() {
		super();
	}

	public Userupdate(User user) {
		if(user!=null) {
			this.email = user.getEmail();	
		}else {
			LOGGER.warn("user was null during User Update create");
		}
		
	}

	@Override
	public String toString() {
		return "Userupdate [email=" + email + ", password=" + password + ", newPassword=" + newPassword + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((newPassword == null) ? 0 : newPassword.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Userupdate other = (Userupdate) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (newPassword == null) {
			if (other.newPassword != null)
				return false;
		} else if (!newPassword.equals(other.newPassword))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@NotNull(message="Lösenord är obligatoriskt fällt")
	@Length(min=6,message="Lösenordet ska va minst 6 tecken")
	private String password;
	
	@NotNull(message="Lösenord är obligatoriskt fällt")
	@Length(min=6,message="Lösenordet ska va minst 6 tecken")
	private String newPassword;


}
