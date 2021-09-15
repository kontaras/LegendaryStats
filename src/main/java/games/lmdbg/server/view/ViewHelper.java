package games.lmdbg.server.view;

import org.springframework.context.annotation.Configuration;

import com.google.common.base.Strings;

import games.lmdbg.server.model.game.Namable;

/**
 * Helper functions for advanced/duplicated functionality in page rendering.
 */
@Configuration
public class ViewHelper {

	/**
	 * Give the proper HTML formatted representation of an {@link}
	 * 
	 * @param item Item to name
	 * @return All the item names (if any), with the proper HTML classes applied
	 */
	public String getDisplayName(Namable item) {
		StringBuilder builder = new StringBuilder();

		if (!Strings.isNullOrEmpty(item.getMarvelName())) {
			builder.append(String.format("<span class=\"marvel\">%s</span>", item.getMarvelName()));
		}

		if (!Strings.isNullOrEmpty(item.getMcuName())) {
			builder.append(String.format("<span class=\"mcu\">%s</span>", item.getMcuName()));
		}

		if (!Strings.isNullOrEmpty(item.getDxpName())) {
			builder.append(String.format("<span class=\"dxp\">%s</span>", item.getDxpName()));
		}

		return builder.toString();
	}

}
