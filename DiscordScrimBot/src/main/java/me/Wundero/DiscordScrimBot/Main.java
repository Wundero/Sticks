/*
 The MIT License (MIT)

 Copyright (c) 2017 Wundero

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package me.Wundero.DiscordScrimBot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.Image;
import sx.blah.discord.util.MessageBuilder;

/**
 * @author Wundero
 *
 */
public class Main {

	private IDiscordClient client;

	private Main(final String token) {
		this.client = Utils.createClient(token, true);
		client.changeUsername("Sticks");
		client.changeAvatar(Image.forUrl("png", "https://puu.sh/xLCDv/447465e36f.png"));
		client.getDispatcher().registerListener(new ChatListener());
	}

	private static Main instance;

	public static Main getInstance() {
		return instance;
	}

	public static Main get() {
		return getInstance();
	}

	public void startScrim(IUser user, IChannel msg, int size, IGuild guild) {
		if (user.getClient().getConnectedVoiceChannels().isEmpty()) {
			return;
		}
		IVoiceChannel originalChannel = Utils.getVoiceChannel(user, guild);
		if (originalChannel == null) {
			new MessageBuilder(client).withContent("You must be in a voice channel to start a scrim!").withChannel(msg)
					.send();
			return;
		}
		List<IUser> users = originalChannel.getConnectedUsers();
		int siz = users.size();
		boolean odd = siz % 2 != 0;
		if (odd) {
			new MessageBuilder(client).withContent("You cannot start a scrim with an odd number of players!")
					.withChannel(msg).send();
			return;
		}
		if (siz % size != 0 || siz == size) {
			new MessageBuilder(client)
					.withContent("You cannot start a " + size + " player per team scrim with this number of players!")
					.withChannel(msg).send();
			return;
		}
		String chPrefix = user.getDisplayName(guild) + "'s Scrim Team #";
		Collections.shuffle(users);
		int teamx = siz / size;
		List<List<IUser>> teams = new ArrayList<>();
		for (int i = 0; i < teamx; i++) {
			teams.add(new ArrayList<>());
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < teamx; j++) {
				int k = j + (teamx * i);
				teams.get(j).add(users.get(k));
			}
		}
		List<IVoiceChannel> chs = new ArrayList<>();
		for (int i = 0; i < teamx; i++) {
			IVoiceChannel ch = guild.createVoiceChannel(chPrefix + (i + 1));
			chs.add(ch);
			teams.get(i).forEach(u -> u.moveToVoiceChannel(ch));
		}
		new MessageBuilder(client).withContent("Scrim has started!").withChannel(msg).send();
	}

	public static void main(String[] args) {
	}

}
