package kon.legendarystatsserver.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import kon.legendarystatsserver.model.game.INamable;

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
	public String getDisplayName(INamable item) {
		List<String> components = new ArrayList<>(3);
		
		if (item.getMarvelName() != null && item.getMarvelName().length() > 0) {
			components.add(String.format("<span class=\"marvel\">%s</span>", item.getMarvelName()));
		}
		
		if (item.getMcuName() != null && item.getMcuName().length() > 0) {
			components.add(String.format("<span class=\"mcu\">%s</span>", item.getMcuName()));
		}
		
		if (item.getDxpName() != null && item.getDxpName().length() > 0) {
			components.add(String.format("<span class=\"dxp\">%s</span>", item.getDxpName()));
		}
		
		
		return String.join(" / ", components);
	}

}
