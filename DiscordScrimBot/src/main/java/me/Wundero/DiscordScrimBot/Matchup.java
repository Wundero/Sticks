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

/**
 * @author Wundero
 *
 */
public class Matchup {

	private final int teamOne, teamTwo;

	public Matchup(int one, int two) {
		this.teamOne = one;
		this.teamTwo = two;
	}

	public Matchup(Integer one, Integer two) {
		this(one.intValue(), two.intValue());
	}

	public Integer getTeamOne() {
		return teamOne;
	}

	public Integer getTeamTwo() {
		return teamTwo;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Matchup)) {
			return false;
		}
		int otherOne = ((Matchup) other).teamOne;
		int otherTwo = ((Matchup) other).teamTwo;
		if (otherOne == teamOne) {
			if (otherTwo == teamTwo) {
				return true;
			}
		}
		if (otherOne == teamTwo) {
			if (otherTwo == teamOne) {
				return true;
			}
		}
		return false;
	}

}
