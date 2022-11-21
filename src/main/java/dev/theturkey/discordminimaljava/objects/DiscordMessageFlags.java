package dev.theturkey.discordminimaljava.objects;

public class DiscordMessageFlags
{
	/**
	 * This message has been published to subscribed channels (via Channel Following)
	 */
	public static long CROSSPOSTED = 1;
	/**
	 * This message originated from a message in another channel (via Channel Following)
	 */
	public static long IS_CROSSPOST = 1 << 1;
	/**
	 * Do not include any embeds when serializing this message
	 */
	public static long SUPPRESS_EMBEDS = 1 << 2;
	/**
	 * The source message for this crosspost has been deleted (via Channel Following)
	 */
	public static long SOURCE_MESSAGE_DELETED = 1 << 3;
	/**
	 * This message came from the urgent message system
	 */
	public static long URGENT = 1 << 4;
	/**
	 * This message has an associated thread, with the same id as the message
	 */
	public static long HAS_THREAD = 1 << 5;
	/**
	 * This message is only visible to the user who invoked the Interaction
	 */
	public static long EPHEMERAL = 1 << 6;
	/**
	 * This message is an Interaction Response and the bot is "thinking"
	 */
	public static long LOADING = 1 << 7;
	/**
	 * This message failed to mention some roles and add their members to the thread
	 */
	public static long FAILED_TO_MENTION_SOME_ROLES_IN_THREAD = 1 << 8;
}
