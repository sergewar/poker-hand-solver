package com.sss.holdem.utils;

import com.sss.holdem.rules.Rule;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RoundParserTest {

    private static Object[][] testDataNegativeCasesWithoutOmaha() {
        return new Object[][]{
                {""},
                {"2"},
                {"3C"},
                {"4d"},
                {"AsKdJhThTd"},
                {"AsKdJhThTd QsQhJdJs 2s3h4d3s"},
                {"AsKdJhThTd QsQhJdJs 2"},
                {"AsKdJhThTd QsQs"},
                {"AsKdJhThTd4s QsQh"},
        };
    }

    private static Object[][] testDataNegativeCasesWithOmaha() {
        return new Object[][]{
                {""},
                {"2"},
                {"3C"},
                {"4d"},
                {"AsKdJhThTd"},
                {"AsKdJhThTd QsQh 2s3h"},
                {"AsKdJhThTd QsQhJdJs 2"},
                {"AsKdJhThTd QsQsJdJs"},
                {"AsKdJhThTd4s QsQh"},
        };
    }

    private static Object[][] testDataPositiveCasesWithoutOmaha() {
        return new Object[][]{
                {"AsKdJhThTd QsQh"},
                {"AsKdJhThTd QsQh 2s3h"},
                {"2c2d2h2s3c 3d3h 3s4c 4d4h 4s5c 5d5h 5s6c 6d6h 6s7c 7d7h 7s8c 8d9h 9sTc TdTh TsJc JdJh JsQc QdQh QsKc " +
                        "KdKh KsAc AdAh"},
        };
    }

    private static Object[][] testDataPositiveCasesWithOmaha() {
        return new Object[][]{
                {"AsKdJhThTd QsQh2s3h"},
                {"AsKdJhThTd QsQh2s3h JsJc6d7c"},
                {"2c2d2h2s3c 3d3h3s4c 4d4h4s5c 5d5h5s6c 6d6h6s7c 7d7h7s8c 8d9h9sTc TdThTsJc JdJhJsQc QdQhQsKc KdKhKsAc"},
        };
    }

    @ParameterizedTest
    @MethodSource("testDataNegativeCasesWithoutOmaha")
    void testParserWithoutOmahaNegativeCases(final String line) {
        final boolean isOmahaRule = false;
        final Rule rule = new Rule(isOmahaRule);
        final RoundParser parser = new RoundParser(rule);

        final var parseResult = parser.parse(line);
        assertTrue(parseResult.isLeft(), "This line should not be parsed: " + line + "\n" +
                "Parsed result is: " + (parseResult.isRight() ? parseResult.get() : ""));
    }

    @ParameterizedTest
    @MethodSource("testDataNegativeCasesWithOmaha")
    void testParserWithOmahaNegativeCases(final String line) {
        final boolean isOmahaRule = true;
        final Rule rule = new Rule(isOmahaRule);
        final RoundParser parser = new RoundParser(rule);

        final var parseResult = parser.parse(line);
        assertTrue(parseResult.isLeft(), "This line should not be parsed: " + line + "\n" +
                "Parsed result is: " + (parseResult.isRight() ? parseResult.get() : ""));
    }

    @ParameterizedTest
    @MethodSource("testDataPositiveCasesWithoutOmaha")
    void testParserWithoutOmaha(final String line) {
        final boolean isOmahaRule = false;
        final Rule rule = new Rule(isOmahaRule);
        final RoundParser parser = new RoundParser(rule);

        final var parseResult = parser.parse(line);
        assertTrue(parseResult.isRight(), "This line should be parsed: " + line + "\n" +
                "Parse error is: " + (parseResult.isLeft() ? parseResult.getLeft() : ""));
    }

    @ParameterizedTest
    @MethodSource("testDataPositiveCasesWithOmaha")
    void testParserWithOmaha(final String line) {
        final boolean isOmahaRule = true;
        final Rule rule = new Rule(isOmahaRule);
        final RoundParser parser = new RoundParser(rule);

        final var parseResult = parser.parse(line);
        assertTrue(parseResult.isRight(), "This line should be parsed:" + line + "\n" +
                "Parse error is: " + (parseResult.isLeft() ? parseResult.getLeft() : ""));
    }
}
