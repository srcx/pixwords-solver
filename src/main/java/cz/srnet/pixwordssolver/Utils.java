package cz.srnet.pixwordssolver;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public class Utils {

	private Utils() {
		// singleton
	}

	@NonNull
	public static <T> T notNull(@Nullable T obj) {
		if (obj == null) {
			throw new NullPointerException();
		}
		return obj;
	}

}
