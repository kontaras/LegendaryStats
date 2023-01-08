package games.lmdbg.server.view;

import java.util.Collection;
import java.util.Collections;

import org.springframework.context.annotation.Configuration;

import com.google.common.base.Strings;

import games.lmdbg.server.model.game.Hero;
import games.lmdbg.server.model.game.Namable;
import games.lmdbg.server.model.game.Team;

/**
 * Helper functions for advanced/duplicated functionality in page rendering.
 */
@Configuration
@SuppressWarnings("static-method")
public class ViewHelper {

	/**
	 * Give the proper HTML formatted representation of an {@link Namable}
	 * 
	 * @param item Item to name
	 * @return All the item names (if any), with the proper HTML classes applied
	 */
	public String getDisplayName(Namable item) {
		if (item instanceof Hero) {
			return getDisplayName((Hero) item);
		}
		
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
	
	/**
	 * Special case of {@link #getDisplayName(Namable)} for {@link Hero} to include the {@link Team}
	 * 
	 * @see #getDisplayName(Namable)
	 * 
	 * @param hero Hero to name
	 * @return All the item names (if any), with the proper HTML classes applied
	 */
	public String getDisplayName(Hero hero) {
		StringBuilder builder = new StringBuilder();
		Team team = hero.getTeam();
		
		if (!Strings.isNullOrEmpty(hero.getMarvelName())) {
			if (Strings.isNullOrEmpty(team.getMarvelName())) {
				builder.append(String.format("<span class=\"marvel\">%s</span>", hero.getMarvelName()));
			} else {
				builder.append(String.format("<span class=\"marvel\">%s (%s)</span>", hero.getMarvelName(), team.getMarvelName()));
			}
		}

		if (!Strings.isNullOrEmpty(hero.getMcuName())) {
			if (Strings.isNullOrEmpty(team.getMcuName())) {
				builder.append(String.format("<span class=\"mcu\">%s</span>", hero.getMcuName()));
			} else {
				builder.append(String.format("<span class=\"mcu\">%s (%s)</span>", hero.getMcuName(), team.getMcuName()));
			}
		}

		if (!Strings.isNullOrEmpty(hero.getDxpName())) {
			if (Strings.isNullOrEmpty(team.getDxpName())) {
				builder.append(String.format("<span class=\"dxp\">%s</span>", hero.getDxpName()));
			} else {
				builder.append(String.format("<span class=\"dxp\">%s (%s)</span>", hero.getDxpName(), team.getDxpName()));
			}
		}

		return builder.toString();
	}
	
	@SuppressWarnings("rawtypes")
	public Collection asCollection(Object o) {
		if (o == null) {
			return Collections.emptySet();
		} else if (o instanceof Collection col) {
			return col;
		}
		
		return Collections.singleton(o);
	}

}
