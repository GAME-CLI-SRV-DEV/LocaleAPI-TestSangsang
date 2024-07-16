package sawfowl.localeapi.api;


import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;

@ConfigSerializable
public interface LocaleReference {

	default Component deserialize(String string) {
		return TextUtils.deserialize(string);
	}

	default Text text(Component component) {
		return Text.of(component);
	}

	default Text text(String string) {
		return Text.of(string);
	}

	default Component replace(Component component, String key, Component value) {
		return component.replaceText(TextReplacementConfig.builder().match(key).replacement(value).build());
	}

	default Component replace(Component component, String key, String value) {
		return replace(component, key, deserialize(value));
	}

	default Component replace(Component component, String key, Object value) {
		return component.replaceText(TextReplacementConfig.builder().match(key).replacement(value.toString()).build());
	}

	default Component replace(Component component, String[] keys, Component... values) {
		return replace(component, replaceMap(keys, values));
	}

	default Component replace(Component component, String[] keys, String... values) {
		return replace(component, replaceMap(keys, values));
	}

	default Component replace(Component component, String[] keys, Object... values) {
		return replace(component, replaceMap(keys, values));
	}

	default String replaceToPlain(Component component, String[] keys, Component... values) {
		return TextUtils.clearDecorations(replaceComponents(component, replaceMapComponents(keys, values)));
	}

	default String replaceToPlain(Component component, String[] keys, String... values) {
		return TextUtils.clearDecorations(replace(component, replaceMap(keys, values)));
	}

	default String replaceToPlain(Component component, String[] keys, Object... values) {
		return TextUtils.clearDecorations(replace(component, replaceMap(keys, values)));
	}

	default <T> T[] array(@SuppressWarnings("unchecked") T... objects) {
		return objects;
	}

	private Component replace(Component component, Map<String, String> map) {
		for(Entry<String, String> entry : map.entrySet()) component = replace(component, entry.getKey(), entry.getValue());
		return component;
	}

	private Component replaceComponents(Component component, Map<String, Component> map) {
		for(Entry<String, Component> entry : map.entrySet()) component = replace(component, entry.getKey(), entry.getValue());
		return component;
	}

	private Map<String, String> replaceMap(String[] keys, Object[] values) {
		return IntStream.range(0, keys.length).boxed().collect(Collectors.toMap(i -> keys[i], i -> values.length > i ? values[i].toString() : ""));
	}

	private Map<String, Component> replaceMapComponents(String[] keys, Component[] values) {
		return IntStream.range(0, keys.length).boxed().collect(Collectors.toMap(i -> keys[i], i -> values.length > i ? values[i] : Component.empty()));
	}

}
