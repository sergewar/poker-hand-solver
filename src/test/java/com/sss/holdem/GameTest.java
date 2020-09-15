package com.sss.holdem;

import com.sss.holdem.card.Card;
import com.sss.holdem.player.PlayerWithRank;
import com.sss.holdem.round.Round;
import com.sss.holdem.round.RoundPlayersWithRank;
import com.sss.holdem.rules.Rule;
import com.sss.holdem.utils.RoundParser;
import io.vavr.control.Either;
import lombok.SneakyThrows;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GameTest {

    private static Object[][] fileWithData() {
        return new Object[][]{
                {false, "rounds/th/th", "rounds/th/th_out"},
                {true, "rounds/omaha/omaha", "rounds/omaha/omaha_out"},
        };
    }

    private String resultForRound(final Round round) {
        if (round.isValid()) {
            final List<PlayerWithRank> pwr = round.getPlayersRank();
            return new RoundPlayersWithRank(pwr).toString();
        } else {
            return "";
        }
    }

    @ParameterizedTest
    @MethodSource("fileWithData")
    @SneakyThrows
    void gameRoundTest(final boolean isOmahaRule, final String fileWithRoundsName, final String fileWithResultsName) {
        final SoftAssertions softAssertions = new SoftAssertions();

        final Rule rule = new Rule(isOmahaRule);
        final RoundParser roundParser = new RoundParser(rule);

        final File fileWithRounds = new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileWithRoundsName)).getFile());
        final Scanner scannerRoundsFile = new Scanner(fileWithRounds);

        final File fileWithResults = new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileWithResultsName)).getFile());
        final Scanner scannerResultsFile = new Scanner(fileWithResults);

        int lineNumber = 0;
        while (scannerRoundsFile.hasNextLine() && scannerResultsFile.hasNextLine()) {
            lineNumber++;
            final String lineWithRound = scannerRoundsFile.nextLine();
            final String expectedResult = scannerResultsFile.nextLine();
            final Either<String, Round> round = roundParser.parse(lineWithRound);
            final String actualResult = round.isLeft() ? "" : resultForRound(round.get());

            softAssertions
                    .assertThat(actualResult)
                    .isEqualTo(expectedResult)
                    .describedAs("Game combination wrong at line: " + lineNumber)
                    .as("Game combination wrong at line: " + lineNumber);
        }
        softAssertions.assertAll();
        System.out.println("Checked " + lineNumber + " lines");
    }

    /**
     * Additional method for generate rounds
     */
    //    @Test
    void generateRounds() {
        final int countTHGames = 50000;
        final int countOmahaGames = 25000;

        final List<Round> roundsTH = new ArrayList<>();
        for (int i = 0; i < countTHGames; i++) {
            roundsTH.add(new RoundCreator(new Rule(false), 2 + new Random().nextInt(22), new Deck()).getRound());
        }
        writeRounds(roundsTH, "th");
        final List<Round> roundsOmaha = new ArrayList<>();
        for (int i = 0; i < countOmahaGames; i++) {
            roundsOmaha.add(new RoundCreator(new Rule(true), 2 + new Random().nextInt(10), new Deck()).getRound());
        }
        writeRounds(roundsOmaha, "omaha");
    }

    @SneakyThrows
    private void writeRounds(final List<Round> rounds, final String fileName) {
        final FileOutputStream fos = new FileOutputStream(new File(fileName));

        for (final Round round : rounds) {
            final String boardCardsString = round.getBoard()
                    .getBoardCards().stream()
                    .map(Card::toString)
                    .collect(Collectors.joining());
            final String playersCardsString = round.getPlayers().stream()
                    .map(player -> player.getCards().stream()
                            .map(Card::toString)
                            .collect(Collectors.joining()))
                    .collect(Collectors.joining(" "));
            fos.write((boardCardsString + " " + playersCardsString).getBytes());
            fos.write("\n".getBytes());
        }

        fos.close();
    }
}
