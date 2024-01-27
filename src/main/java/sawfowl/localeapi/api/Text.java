package sawfowl.localeapi.api;

import java.util.function.Consumer;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.adventure.SpongeComponents;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.data.persistence.DataSerializable;

import net.kyori.adventure.builder.AbstractBuilder;
import net.kyori.adventure.text.Component;
import sawfowl.localeapi.api.placeholders.Placeholders;
import sawfowl.localeapi.api.placeholders.Placeholders.DefaultPlaceholderKeys;

public interface Text extends DataSerializable {

	private static Builder builder() {
		return Sponge.game().builderProvider().provide(Builder.class);
	}

	static Text of(Component component) {
		return builder().fromComponent(component);
	}

	static Text of(String string) {
		return builder().fromString(string);
	}

	/**
	 * Exiting from the editor and retrieve the {@link Component} object.
	 */
	Component get();

	/**
	 * Exiting from the editor and getting a string without any decoration or additional functionality.<br>
	 * Appropriate for sending a message to the console.
	 */
	String toPlain();

	/**
	 * Appends a component to this text.
	 */
	Text append(Component component);

	/**
	 * Appends a text to this text.
	 */
	Text append(Text text);

	/**
	 * This method works similarly to {@link String#replace(String, String)}
	 */
	Text replace(String key, String value);

	/**
	 * This method works similarly to {@link String#replace(String, String)}
	 */
	Text replace(String key, Component value);

	/**
	 * This method works similarly to {@link String#replace(String, String)}
	 */
	Text replace(String key, Text value);

	/**
	 * This method works similarly to {@link String#replace(String, String)}
	 */
	Text replace(String key, Object value);

	/**
	 * This method works similarly to {@link String#replace(String, String)}
	 */
	Text replace(String[] keys, String... values);

	/**
	 * This method works similarly to {@link String#replace(String, String)}
	 */
	Text replace(String[] keys, Object... values);

	/**
	 * This method works similarly to {@link String#replace(String, String)}
	 */
	Text replace(String[] keys, Component... values);

	/**
	 * This method works similarly to {@link String#replace(String, String)}
	 */
	Text replace(String[] keys, Text... values);

	/**
	 * Adding the execution of arbitrary code when you click on text.<br>
	 * It is used {@link #createCallBack(Consumer)}
	 */
	Text createCallBack(Runnable runnable);

	/**
	 * Adding the execution of arbitrary code when you click on text.<br>
	 * It is used {@link SpongeComponents#executeCallback(callback)}
	 */
	Text createCallBack(Consumer<CommandCause> callback);

	/**
	 * Removing all decorations from the text.
	 */
	Text removeDecorations();

	default Text replace(Placeholders.DefaultPlaceholderKeys key, String value) {
		return replace(key.textKey(), value);
	}

	default Text replace(DefaultPlaceholderKeys key, Component component) {
		return replace(key.textKey(), component);
	}

	default Text replace(DefaultPlaceholderKeys key, Object object) {
		return replace(key.textKey(), object);
	}

	default <T> Text applyPlaceholders(T target, Component def) {
		return Placeholders.apply(this, target, def);
	}

	default <T> Text applyPlaceholders(Component def, Object... args) {
		return Placeholders.apply(this, def, args);
	}

	public interface Builder extends AbstractBuilder<Text>, org.spongepowered.api.util.Builder<Text, Builder> {

		Text fromComponent(Component component);

		Text fromString(String string);

	}

}
