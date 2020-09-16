package com.sss.holdem.benchmark;

import com.sss.holdem.Deck;
import com.sss.holdem.RoundCreator;
import com.sss.holdem.player.PlayerWithRank;
import com.sss.holdem.round.Round;
import com.sss.holdem.round.checkers.CombinationRank;
import com.sss.holdem.rules.Rule;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.WarmupMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 1, time = 3)
@Measurement(iterations = 7, time = 7)
@Threads(value = 4)
public class BenchMark {

    private final List<Round> DATA_FOR_TESTING = new ArrayList<>();
    private final EnumMap<CombinationRank, Integer> playersResults = new EnumMap<>(CombinationRank.class);
    private final List<List<PlayerWithRank>> roundResults = Collections.synchronizedList(new ArrayList<>());

    @Param({"1000"})
    private int N;

    @Param({"false", "true"})
    private boolean isOmaha;

    public static void main(String[] args) throws RunnerException {
        final Options opt = new OptionsBuilder()
                .include(BenchMark.class.getSimpleName())
                .warmupMode(WarmupMode.BULK)
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MILLISECONDS)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        for (final CombinationRank cr : CombinationRank.values()) {
            playersResults.put(cr, 0);
        }

        System.out.println("Omaha rule in setup: " + isOmaha);
        final int maxPlayers = isOmaha ? 10 : 22;
        for (int i = 0; i < N; i++) {
            DATA_FOR_TESTING.add(
                    new RoundCreator(
                            new Rule(isOmaha),
                            2 + new Random().nextInt(maxPlayers),
                            new Deck()
                    ).getRound()
            );
        }
    }

    @Benchmark
    public void measureRoundsSolver() {
        for (final Round round : DATA_FOR_TESTING) {
            roundResults.add(round.getPlayersRank());
        }
    }

    @Benchmark
    public void measureRoundsSolver2() {
        for (final Round round : DATA_FOR_TESTING) {
            roundResults.add(round.getPlayersRank2());
        }
    }

    @TearDown
    public void tearDown() {
        for (final List<PlayerWithRank> roundResult : roundResults) {
            for (final PlayerWithRank playerWithRank : roundResult) {
                final CombinationRank cr = playerWithRank.getCombinationAndCards()._1;
                playersResults.put(cr, playersResults.get(cr) + 1);
            }
        }

        playersResults.forEach((key, value) -> System.out.println("Game combination: " + key + " count:" + value));
    }

}
