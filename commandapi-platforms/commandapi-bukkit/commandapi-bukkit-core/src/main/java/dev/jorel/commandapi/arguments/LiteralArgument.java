/*******************************************************************************
 * Copyright 2018, 2020 Jorel Ali (Skepter) - MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package dev.jorel.commandapi.arguments;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.jorel.commandapi.exceptions.BadLiteralException;
import dev.jorel.commandapi.executors.CommandArguments;

/**
 * A pseudo-argument representing a single literal string
 * 
 * @since 1.3
 */
public class LiteralArgument extends Argument<String> implements Literal<Argument<String>> {
	private final String literal;

	/**
	 * A literal argument. Only takes one string value which cannot be modified
	 *
	 * @param literal the string literal that this argument will represent
	 */
	public LiteralArgument(final String literal) {
		/*
		 * The literal argument builder is NOT technically an argument.
		 * Therefore, it doesn't have an ArgumentType.
		 *
		 * This is a wrapper for the object "LiteralArgumentBuilder<>"
		 */
		super(literal, null);

		if (literal == null) {
			throw new BadLiteralException(true);
		}
		if (literal.isEmpty()) {
			throw new BadLiteralException(false);
		}
		this.literal = literal;
		this.setListed(false);
	}


	@Override
	public Class<String> getPrimitiveType() {
		return String.class;
	}

	/**
	 * A utility method to create a literal argument. Works as an alternative to {@link dev.jorel.commandapi.arguments.LiteralArgument#literal(String)}
	 * <p>
	 * To provide easier use of this method you can statically import this: {@code import static dev.jorel.commandapi.arguments.LiteralArgument.of;}
	 *
	 * @param literal the string literal that this argument will represent
	 * @return the literal argument created by this method
	 */
	public static LiteralArgument of(final String literal) {
		return new LiteralArgument(literal);
	}

	/**
	 * A utility method to create a literal argument. Works as an alternative to {@link dev.jorel.commandapi.arguments.LiteralArgument#of(String)}
	 * <p>
	 * To provide easier use of this method you can statically import this: {@code import static dev.jorel.commandapi.arguments.LiteralArgument.literal;}
	 *
	 * @param literal the string literal that this argument will represent
	 * @return the literal argument created by this method
	 */
	public static LiteralArgument literal(final String literal) {
		return new LiteralArgument(literal);
	}

	/**
	 * Returns the literal string represented by this argument
	 *
	 * @return the literal string represented by this argument
	 */
	@Override
	public String getLiteral() {
		return literal;
	}

	@Override
	public CommandAPIArgumentType getArgumentType() {
		return CommandAPIArgumentType.LITERAL;
	}

	@Override
	public <Source> String parseArgument(CommandContext<Source> cmdCtx, String key, CommandArguments previousArgs) throws CommandSyntaxException {
		return literal;
	}
}
