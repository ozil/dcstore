// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.ejb;

import javax.ejb.Local;

@Local
public interface CategoryBean {
	public void add(String name);
}

