package com.sss.holdem;

import com.sss.holdem.player.PlayerWithRank;
import com.sss.holdem.round.Round;
import com.sss.holdem.round.RoundPlayersWithRank;
import com.sss.holdem.rules.Rule;
import com.sss.holdem.utils.ConsoleReader;
import com.sss.holdem.utils.ConsoleWriter;
import com.sss.holdem.utils.FFileReader;
import com.sss.holdem.utils.FFileWriter;
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

        final String fileIn = System.getProperty("fileIn", "");
        final String fileOut = System.getProperty("fileOut", "");
        if (fileIn.equals(fileOut)) {
            throw new IllegalArgumentException("Input file and output file should be different but they have the same name " + fileIn);
        }
        final Reader reader = !"".equals(fileIn) ? new FFileReader(fileIn) : new ConsoleReader();
        final Writer writer = !"".equals(fileOut) ? new FFileWriter(fileOut) : new ConsoleWriter();

        try {
            String s;
            while ((s = reader.readLine()) != null) {
                final Either<String, Round> round = roundParser.parse(s);
                if (round.isLeft()) {
                    writer.writeLine(round.getLeft());
                } else {
                    printResultForRound(writer, round.get());
                }
            }
        } finally {
            reader.close();
            writer.close();
        }
    }

    private static void printResultForRound(final Writer writer, final Round round) {
        if (round.isValid()) {
            final List<PlayerWithRank> pwr = round.getPlayersRank();
            writer.writeLine(new RoundPlayersWithRank(pwr).toString());
        } else {
            writer.writeLine(round.getRoundInfo());
        }
    }
}
