package com.sss.holdem;

import com.sss.holdem.player.PlayerWithRank;
import com.sss.holdem.round.Round;
import com.sss.holdem.round.RoundPlayersWithRank;
import com.sss.holdem.rules.Rule;
import com.sss.holdem.utils.ConsoleReader;
import com.sss.holdem.utils.ConsoleWriter;
import com.sss.holdem.utils.Reader;
import com.sss.holdem.utils.RoundParser;
import com.sss.holdem.utils.Writer;
import io.vavr.control.Either;

import java.util.List;

public class Runner {
    public static void main(final String[] args) {
        final boolean isOmahaRule = List.of(args).contains("--omaha");
        final Rule rule = new Rule(isOmahaRule);
        final RoundParser roundParser = new RoundParser(rule);

        final Reader consoleReader = new ConsoleReader();
        final Writer consoleWriter = new ConsoleWriter();
        consoleWriter.writeLine("Output:");

        String s;
        while ((s = consoleReader.readLine()) != null) {
            final Either<String, Round> round = roundParser.parse(s);
            if (round.isLeft()) {
                System.out.println(round.getLeft());
            } else {
                printResultForRound(round.get());
            }
        }
    }

    public static void printResultForRound(final Round round) {
        final Writer consoleWriter = new ConsoleWriter();
        if (round.isValid()) {
            final List<PlayerWithRank> pwr = round.getPlayersRank();
            consoleWriter.writeLine(new RoundPlayersWithRank(pwr).toString());
        } else {
            consoleWriter.writeLine(round.getRoundInfo());
        }
    }
}
