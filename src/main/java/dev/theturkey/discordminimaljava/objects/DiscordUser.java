package dev.theturkey.discordminimaljava.objects;

public class DiscordUser
{
	/**
	 * The user's id
	 */
	public String id;
	/**
	 * The user's username, not unique across the platform
	 */
	public String username;
	/**
	 * The user's 4-digit discord-tag
	 */
	public String discriminator;

	// public avatar ? string	the user's avatar hash	identify

	/**
	 * whether the user belongs to an OAuth2 application	identify
	 */
	public boolean bot;

	// public system ? boolean	whether the user is an Official Discord System user(part of the urgent message system)	identify
	// public mfa_enabled ? boolean	whether the user has two factor enabled on their account	identify
	// public banner ?	? string	the user's banner hash	identify
	// public accent_color ?	? integer	the user's banner color encoded as an integer representation of hexadecimal color code	identify
	// public locale ? string	the user's chosen language option	identify
	// public verified ? boolean	whether the email on this account has been verified	email
	// public email ?	? string	the user's email	email
	// public flags ? integer	the flags on a user's account	identify
	// public premium_type ? integer	the type of Nitro subscription on a user's account	identify
	// public public_flags ? integer	the public flags on a user's account	identify
}
