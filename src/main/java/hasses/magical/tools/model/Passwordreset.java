package hasses.magical.tools.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class Passwordreset implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((passwordRepeat == null) ? 0 : passwordRepeat.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
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
		Passwordreset other = (Passwordreset) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (passwordRepeat == null) {
			if (other.passwordRepeat != null)
				return false;
		} else if (!passwordRepeat.equals(other.passwordRepeat))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRepeat() {
		return passwordRepeat;
	}

	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@NotNull(message="Lösenord är obligatoriskt fällt")
	@Length(min=6,message="Lösenordet ska va minst 6 tecken")
	private String password;
	
	@NotNull(message="Lösenord är obligatoriskt fällt")
	@Length(min=6,message="Lösenordet ska va minst 6 tecken")
	private String passwordRepeat;

	@NotNull(message="Hoppsan verkar ha ett felaktigt Tecken")
	private String token;

	public Passwordreset(
			@NotNull(message = "Lösenord är obligatoriskt fällt") @Length(min = 6, message = "Lösenordet ska va minst 6 tecken") String password,
			@NotNull(message = "Lösenord är obligatoriskt fällt") @Length(min = 6, message = "Lösenordet ska va minst 6 tecken") String passwordRepeat,
			@NotNull(message = "Hoppsan verkar ha ett felaktigt Tecken") String token) {
		super();

		this.password = password;
		this.passwordRepeat = passwordRepeat;
		this.token = token;
	}
	
	public Passwordreset() {
		super();
	}

}
