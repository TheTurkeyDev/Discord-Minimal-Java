package dev.theturkey.discordminimaljava;

import dev.theturkey.discordminimaljava.objects.DiscordChannel;
import dev.theturkey.discordminimaljava.objects.DiscordGuild;
import dev.theturkey.discordminimaljava.objects.DiscordIntents;

public class TestBot extends DiscordMinimal
{

	public TestBot()
	{
		super(new long[]{DiscordIntents.GUILDS, DiscordIntents.GUILD_MESSAGES, DiscordIntents.GUILD_MESSAGE_REACTIONS});
		System.out.println("Starting Discord Bot...");
		login(Token.token);
	}

	@Override
	public void onGuildCreate(DiscordGuild guild)
	{
		System.out.println(guild.name);
		for(DiscordChannel c : guild.channels)
		{
			System.out.println("\t" + c.name + " (" + c.type +")");
		}
	}

	public static void main(String[] args)
	{
		new TestBot();
	}

}
