package User;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class User implements Serializable {
		private String name;
		private ImageIcon image;
		
		public User(String name, ImageIcon image) {
			this.name = name;
			this.image = image;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public ImageIcon getImage() {
			return image;
		}
		
		public void setImage(ImageIcon image) {
			this.image = image;
		}
		
		public int hashCode() {
			return name.hashCode();
		}
		
		public boolean equals(Object obj) {
			if(obj instanceof User) {
				return name.equals(((User) obj).getName());
			}
			return false;
		}
	}

