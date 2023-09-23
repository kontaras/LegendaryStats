package games.lmdbg.server.view;

import java.util.Collection;
import java.util.Collections;

import org.springframework.context.annotation.Configuration;

import com.google.common.base.Strings;

import games.lmdbg.rules.model.CardSet;
import games.lmdbg.rules.model.Hero;
import games.lmdbg.rules.model.Nameable;
import games.lmdbg.rules.model.Team;


/**
 * Helper functions for advanced/duplicated functionality in page rendering.
 */
@Configuration
@SuppressWarnings("static-method")
public class ViewHelper {

	/**
	 * Give the proper HTML formatted representation of an {@link CardSet}
	 * 
	 * @param item Item to name
	 * @return All the item names (if any), with the proper HTML classes applied
	 */
	public String getDisplayName(Nameable item) {
		if (item instanceof Hero) {
			Hero hero = (Hero) item;
			if (!hero.getTeam().isEmpty()) {
				return getHeroDisplayName(hero);
			}
		}
		
		StringBuilder builder = new StringBuilder();

		if (!Strings.isNullOrEmpty(item.getArtName())) {
			builder.append(String.format("<span class=\"marvel\">%s</span>", item.getArtName()));
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
	 * Special case of {@link #getDisplayName(CardSet)} for {@link Hero} to include the {@link Team}
	 * 
	 * @see #getDisplayName(CardSet)
	 * 
	 * @param hero Hero to name
	 * @return All the item names (if any), with the proper HTML classes applied
	 */
	public String getHeroDisplayName(Hero hero) {
		StringBuilder builder = new StringBuilder();
		Team team = hero.getTeam().get(0);
		
		if (!Strings.isNullOrEmpty(hero.getArtName())) {
			if (Strings.isNullOrEmpty(team.getArtName())) {
				builder.append(String.format("<span class=\"marvel\">%s</span>", hero.getArtName()));
			} else {
				builder.append(String.format("<span class=\"marvel\">%s (%s)</span>", hero.getArtName(), team.getArtName()));
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
