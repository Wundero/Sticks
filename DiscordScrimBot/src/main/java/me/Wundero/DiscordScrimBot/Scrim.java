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
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;

/**
 * @author Wundero
 *
 */
public class Scrim {

	private final IUser creator;
	private final UUID id;
	private final String name;
	private final List<List<IUser>> teams;
	private final IGuild guild;
	private final List<IVoiceChannel> teamChannels;
	private final IVoiceChannel originalChannel;
	private final Consumer<String> sendMessage;
	private final TeamStrategy strategy;
	private final MatchupStrategy matchMaker;
	private final int teamCount, teamSize;
	private final List<Matchup> matches;
	private Iterator<Matchup> matchIter;

	public Scrim(IUser creator, String name, List<IUser> participants, IGuild guild, IVoiceChannel original,
			TeamStrategy teamMaker, MatchupStrategy matchMaker, int teamSize, Consumer<String> msg) {
		this.creator = creator;
		this.id = UUID.randomUUID();
		this.name = name;
		this.teamCount = participants.size() / teamSize;
		this.teamSize = teamSize;
		this.strategy = teamMaker;
		this.teams = teamMaker.createTeams(participants, teamSize, teamCount);
		this.guild = guild;
		this.originalChannel = original;
		this.sendMessage = msg;
		this.matchMaker = matchMaker;
		this.matches = matchMaker.createMatchups(teamCount);
		this.matchIter = matches.iterator();
		this.teamChannels = new ArrayList<>();
		int i = 1;
		for (List<IUser> team : teams) {
			IVoiceChannel teamCh = guild.createVoiceChannel(name + " TEAM #" + i);
			i++;
			teamChannels.add(teamCh);
		}
	}

	public void moveTeams() {
		int i = 0;
		for (List<IUser> team : teams) {
			IVoiceChannel channel = teamChannels.get(i);
			team.forEach(u -> u.moveToVoiceChannel(channel));
			i++;
		}
	}

	private void m(String n) {
		sendMessage.accept(n);
	}

	public void resetMatchups() {
		this.matchIter = matches.iterator();
	}

	public void nextMatchup() {
	}

	public void create() {
	}

	public void start() {
	}

	public void end() {
	}

	public void shuffle() {
	}
}
